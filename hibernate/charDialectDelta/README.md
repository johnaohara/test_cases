TEST CASE
---------

Hibernate dialect delta between postgres and mysql databases

Description
-----------

Database columns defined as Char are handled differently for mysql and postgres dialect. The properties populated in entities are different for the two dialects.

If a column is defined as a fixed length char(n), the mysql dialect populates an enitity property with a variable length string. The postgres dialect populates an entity property with a fixed length string that has the came length as the database column, padded with whitespace after the string that had been persisted.

I would have expected the two dialects to return the same result, whether that is a fixed length string or a variable length string.

How to run test case
--------------------

1) Create a mysql database called chartest

2) Grant a user all privaledges on chartest datbase

3) update ./src/main/resources/hibernate.cfg.mysql.xml with mysql username and password

4) Create a postgres database called chartest

5) Grant a user all privaledges on chartest datbase

6) update ./src/main/resources/hibernate.cfg.postgres.xml with postgres username and password

7) update ./pom.xml set properties postgres.user and postgres.password with postgres username and password

8) update ./pom.xml set properties mysql.user and mysql.password with mysql username and password

9) execute 'mvn test'

