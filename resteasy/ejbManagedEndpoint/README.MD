## Resteasy ejb managed rest endpoint testcase

### To run

If not already set, define JBOSS_HOME env variable

 `$ export JBOSS_HOME={PATH_TO_WILDFLY}`

 Run the tests
 
 `$ mvn clean test`
 
### Expected Result

org.jboss.perf.client.EndpointTest ; fail

org.jboss.perf.client.NonManagedEndpointTest ; pass