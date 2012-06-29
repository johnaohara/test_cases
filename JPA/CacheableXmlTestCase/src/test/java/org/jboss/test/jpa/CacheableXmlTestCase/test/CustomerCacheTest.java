/*
 * Test Case for defining JPA caching
 * 
 * When selective caching and second level cache is enabled through the JPA persistence.xml configuration file, entites that are defined in
 * orm.xml with cacheable="true" attribute are not cached in the second level cache.   
 *
 * Entities that are annotated @Cacheable(true) are cached correctly
 * 
 * This test case test the following 4 scenarios;
 * 		a) Entity is annotated with @Cacheable(true)
 * 		b) Entity is defined as cacheable in orm.xml
 * 		c) Entity is defined as cacheable with hibernate.ejb.classcache in persistence.xml
 * 		d) Entity has not been defined as cacheable and therefore should not be cached
 * 
 * Methodology
 * 
 * For each test;
 * 		1) A new customer is created and persisted through JPA.
 * 		2) A JDBC call is made to update CUST_CREDIT value directly to database by picking a connection from connection pool and executing a SQL statement
 * 		3) The customer entity is retrieved through JPA by finding on PK 
 * 		4) The customer availableCredit property of the retrieved object is compared to the CUST_CREDIT value set in step 2
 * 		5) If availableCredit==CUST_CREDIT, the entity has not been cached. if availableCredit!=CUST_CREDIT, the entity has not been cached.
 * 
 * Expected Results
 * 
 * 		a) Annotated Entity 			cached==true
 * 		b) XML Entity					cached==true
 * 		c) Hibernate defined Entity		cached==true
 * 		d) Not cached Entity			cached==false
 *  
 */

package org.jboss.test.jpa.CacheableXmlTestCase.test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.jpa.CacheableXmlTestCase.model.CustomerAnnotated;
import org.jboss.test.jpa.CacheableXmlTestCase.model.CustomerHibernateCached;
import org.jboss.test.jpa.CacheableXmlTestCase.model.CustomerNotCached;
import org.jboss.test.jpa.CacheableXmlTestCase.model.CustomerXml;
import org.jboss.test.jpa.CacheableXmlTestCase.model.ICustomer;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CustomerCacheTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(CustomerXml.class, CustomerHibernateCached.class,
						CustomerNotCached.class, CustomerAnnotated.class,
						ICustomer.class)
				.addAsResource("persistence.xml", "META-INF/persistence.xml")
				.addAsResource("orm.xml", "META-INF/orm.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	// Values to insert into tables
	Integer custCredit = 100;
	Integer increaseCredit = 50;
	String custName = "Bob Jones";

	@Resource(mappedName = "java:jboss/datasources/ExampleDS")
	DataSource datasource;

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	// xml defined customer entity should be cached
	@Test
	public void testXml() throws Exception {

		CustomerXml xmlCustomer = new CustomerXml(custName, custCredit);

		performCachetest(xmlCustomer, true);

	}

	// annotated customer entity should be cached
	@Test
	public void testAnnotated() throws Exception {

		CustomerAnnotated annotatedCustomer = new CustomerAnnotated(custName,
				custCredit);

		performCachetest(annotatedCustomer, true);

	}

	// hibernate property customer entity should be cached
	@Test
	public void testHibernateCached() throws Exception {

		CustomerHibernateCached hibernateCachedCustomer = new CustomerHibernateCached(
				custName, custCredit);

		performCachetest(hibernateCachedCustomer, true);

	}

	// customer entity with no caching defined should not be cached
	@Test
	public void testNotCached() throws Exception {

		CustomerNotCached notCachedCustomer = new CustomerNotCached(custName,
				custCredit);

		performCachetest(notCachedCustomer, false);

	}

	/*
	 * Perform the cache test for customer
	 * 
	 * customer - newly created customer entity to test whether the it is cached 
	 * shouldBeCached - expected result
	 * 
	 * @author John O'Hara
	 */
	private void performCachetest(ICustomer customer, Boolean shouldBeCached) {

		try {

			utx.begin();

			Integer newBalance = customer.getAvailableCredit() + increaseCredit;

			// persist the new customer
			em.persist(customer);

			utx.commit();

			Long custID = customer.getId();

			Class<?> custClass = customer.getCustomerClass();

			// update customer outside of JPA
			updateTableOutsideJPA(custID, newBalance, customer.getEntityTable());

			// clear reference to customer entity
			customer = null;

			// lookup customer entity through JPA
			ICustomer cust = (ICustomer) em.find(custClass, custID);

			// assert that we have the expected result
			assertTrue(cust.getAvailableCredit().equals(newBalance) != shouldBeCached);

		} catch (Exception e) {
			try {
				utx.rollback();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/*
	 * Update customer table outside of JPA
	 * 
	 * CUST_ID - customer ID of entity that we are testing 
	 * CUST_BALANC - new balance of customer account 
	 * tableName - table name for particular entity
	 * 
	 * @author John O'Hara
	 */
	private void updateTableOutsideJPA(Long CUST_ID, Integer CUST_BALANC,
			String tableName) {
		Connection conn = null;
		try {
			conn = datasource.getConnection();

			conn.setAutoCommit(true);

			String sqlStatement = "update " + tableName + " set CUST_CREDIT = "
					+ CUST_BALANC.toString() + " WHERE CUST_ID="
					+ CUST_ID.toString() + ";";

			PreparedStatement statement = conn.prepareStatement(sqlStatement);

			statement.execute();

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
