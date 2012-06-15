package test.Hibernate_char_testcase;

import java.net.URL;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

import org.jboss.orm.test.dialect.entity.Customer;

/*
 * Test case to test delta between Char sql data types in postgres and mysql
 * This test case performs the same test on both databases
 * 
 */

public class HibernateCharTestCase extends TestCase {

	private static String phoneNumber = "1234";

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	private Session session = null;

	public HibernateCharTestCase() {
	}

	@Test
	public void testHibernateMysqlChar() throws Exception {

		// get Mysql configuration
		URL resourcePath = this.getClass().getResource(
				"/hibernate.cfg.mysql.xml");

		comparePhoneNumber(resourcePath);
	}

	@Test
	public void testHibernatePostgresChar() throws Exception {

		// get postgres configuration
		URL resourcePath = this.getClass().getResource(
				"/hibernate.cfg.postgres.xml");
		comparePhoneNumber(resourcePath);
	}

	/*
	 * comparePhone number between a Customer that we persisted and a 
	 * customer entity that we populated from the database
	 */
	private void comparePhoneNumber(URL resourcePath) throws Exception {
		if (resourcePath == null) {
			throw new Exception("Hibernate configuration file not found");
		} else {
			sessionFactory = configureSessionFactory(resourcePath);

		}

		try {
			session = sessionFactory.openSession();

			/*
			 * Persist a dummy customer, with the phone number defined in phoneNumber
			 */
			Integer customerId = saveCustomer(phoneNumber);

			/*
			 * Retrieve the customer entity using the ID returned from database
			 */
			Customer customer = performLookup(customerId);

			/*
			 * Ensure that we have the correct customer
			 */
			assert (customerId.equals(customer.getId()) == true);

			/*
			 * Check the phone number in the entity against the phone number we persisted
			 */
			assert (phoneNumber.equals(customer.getPhone()) == true);

		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}

	}

	private Customer performLookup(int ID) throws Exception {
		Customer customer = null;
		try {
			Session session = sessionFactory.openSession();

			customer = (Customer) session.load(Customer.class, ID);
		} catch (Exception e) {
			throw e;
		}
		return customer;

	}

	public Integer saveCustomer(String phone) throws Exception {
		Transaction transaction = null;
		Integer customerId = null;
		try {
			transaction = session.beginTransaction();
			Customer customer = new Customer(phone);
			customerId = (Integer) session.save(customer);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			throw e;
		}
		return customerId;
	}

	private static SessionFactory configureSessionFactory(URL resourcePath)
			throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.configure(resourcePath);
		serviceRegistry = new ServiceRegistryBuilder().applySettings(
				configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}
}
