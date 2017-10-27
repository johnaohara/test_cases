package org.jboss.perf.client;

import org.jboss.perf.client.util.AddressGenerator;
import org.jboss.perf.client.util.NameGenerator;
import org.jboss.perf.client.util.RandNum;
import org.jboss.perf.common.Address;
import org.jboss.perf.common.Gender;
import org.jboss.perf.common.PolicyHolder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.jboss.perf.client.util.Util.randw;

/**
 * Created by johara on 27/10/17.
 */
public abstract class PolicyHolderBase extends TestBase {

    static final AddressGenerator addressGenerator = AddressGenerator.getInstance();
    static final NameGenerator nameGenerator = NameGenerator.getInstance();

    public PolicyHolderBase(String GET_URL, String POST_URL) {
        super( GET_URL, POST_URL );
    }

    protected PolicyHolder generatePolicyHolder(Boolean valid){
        String firstName = "John";
        String lastName = "Doe";
        String password = "password";
        String email = nameGenerator.getRandomEmail( firstName, lastName, 0 );

        PolicyHolder newPolicyHolder = new PolicyHolder();

        newPolicyHolder.setFirstName( valid ? firstName : "");
        newPolicyHolder.setMiddleName( new StringBuilder( firstName ).reverse().toString() );
        newPolicyHolder.setLastName( lastName );
        newPolicyHolder.setGender( Gender.FEMALE );

        try {
            newPolicyHolder.setBirthDate( new SimpleDateFormat( "dd-MM-yyyy" ).parse( getNextDate() ) );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Address adr = addressGenerator.generateAddress();

        newPolicyHolder.getAddress().setCity( valid ? adr.getCity() : "");
        newPolicyHolder.getAddress().setStreet1( adr.getStreet1() );
        newPolicyHolder.getAddress().setStreet2( randw.makeNString( 1, 4 ) );
        newPolicyHolder.getAddress().setZip( adr.getZip() );
        newPolicyHolder.getAddress().setState( adr.getState() );
        newPolicyHolder.getAddress().setCountry( randw.getNextCountry() );
        newPolicyHolder.getAddress().setPhone( valid ? randw.getNextPhoneNumber() : "" );
        newPolicyHolder.setEmail( email );
        newPolicyHolder.setPassword( password );

        return  newPolicyHolder;
    }



    protected ResteasyClient createJAXRSClient() {
        return new ResteasyClientBuilder().build();
    }


    protected String getNextDate() {
        RandNum r = new RandNum();
        StringBuilder sb = new StringBuilder();
        int month = r.random( 1, 12 );
        int day = 1;
        if ( month == 2 )
            day = r.random( 1, 28 );
        else
            day = r.random( 1, 30 );
        sb.append( day );     // day
        sb.append( "-" );
        sb.append( month );      // month
        sb.append( "-" );
        sb.append( r.random( 1920, 2010 ) ); // year
        return sb.toString();
    }
}
