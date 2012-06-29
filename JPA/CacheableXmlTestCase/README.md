Test Case for defining JPA caching
==================================

When selective caching and second level cache is enabled through the JPA persistence.xml configuration file, entites that are defined in
orm.xml with cacheable="true" attribute are not cached in the second level cache.   

Entities that are annotated @Cacheable(true) are cached correctly
 
This test case test the following 4 scenarios

1. Entity is annotated with @Cacheable(true)
2. Entity is defined as cacheable in orm.xml
3. Entity is defined as cacheable with hibernate.ejb.classcache in persistence.xml
4. Entity has not been defined as cacheable and therefore should not be cached
 
Methodology
===========

For each test
-------------

1. A new customer is created and persisted through JPA.
2. A JDBC call is made to update CUST_CREDIT value directly to database by picking a connection from connection pool and executing a SQL statement
3. The customer entity is retrieved through JPA by finding on PK 
4. The customer availableCredit property of the retrieved object is compared to the CUST_CREDIT value set in step 2
5. If availableCredit==CUST_CREDIT, the entity has not been cached. if availableCredit!=CUST_CREDIT, the entity has not been cached.
 
Expected Results
================ 
 	a) Annotated Entity 			cached==true
 	b) XML Entity				cached==true
 	c) Hibernate defined Entity		cached==true
 	d) Not cached Entity			cached==false

How to Run Testcase
===================

	Startup a standalone instance of AS7.1.2/EAP6.0 with standalone-full.xml configuration
	$ mvn clean test
