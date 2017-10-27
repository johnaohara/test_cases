package org.jboss.perf.client;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.perf.common.Address;
import org.jboss.perf.common.Gender;
import org.jboss.perf.common.PolicyHolder;
import org.jboss.perf.common.ValidEmail;
import org.jboss.perf.common.Vehicle;
import org.jboss.perf.webapp.ManagedPolicyHolderEndpoint;
import org.jboss.perf.webapp.ManagedRestApplication;
import org.jboss.perf.webapp.VehicleEndpoint;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * Created by johara on 27/10/17.
 */
@RunWith(Arquillian.class)
public class ManagedEndpointTest extends PolicyHolderBase {

    public ManagedEndpointTest() {
        super( null, "http://localhost:8080/test/rest/policyholder/add" );
    }

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create( WebArchive.class, "test.war" )
            .addClasses( ManagedPolicyHolderEndpoint.class
                , ManagedRestApplication.class
                , VehicleEndpoint.class
                , Address.class
                , Gender.class
                , PolicyHolder.class
                , ValidEmail.class
                , Vehicle.class
            )
            .addAsWebInfResource( new File( "src/test/resources/web.xml" ) )
            ;
    }


    @Test
    public void callManagedRestEndpoint() throws Exception {
        validateResponse( 204
            , executePost( generatePolicyHolder( true ) )
        );
        validateResponse( 400
            , executePost( generatePolicyHolder( false ) )
        );
    }
    
}
