package org.jboss.perf.client.util;

import org.jboss.perf.common.Address;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by johara on 20/10/17.
 */
public class AddressGenerator extends  Generator{

    private static final Object LOCK = new Object();
    private static volatile AddressGenerator INSTANCE;

    private final int STREET_NUM = 999;
    private List<String> direction;
    private List<String> streetNames;
    private List<String> streetType;
    private List<String> cityState;

    private AddressGenerator(String dictionary, String cityAndState) {
        generateCityAndState( cityAndState != null ? readFile( cityAndState ) : readFileInJar( "city_state.txt" ) );
        generateStreetName( dictionary != null ? readFile( dictionary ) : readFileInJar( "dictionary_words.txt" ) );
        generateStreetType();
        generateDirections();
    }

    public static AddressGenerator getInstance() {
        return getInstance( null, null );
    }

    public static AddressGenerator getInstance(String dictionary, String cityAndState) {
        if ( INSTANCE == null ) {
            synchronized (LOCK) {
                if ( INSTANCE == null )
                    INSTANCE = new AddressGenerator( dictionary, cityAndState );
            }
        }
        return INSTANCE;
    }

    private int getRandomStreetNumber() {
        return randInt( 1, STREET_NUM );
    }

    private void generateDirections() {
        direction = new ArrayList<>( 8 );
        direction.add( "SE" );
        direction.add( "SW" );
        direction.add( "NE" );
        direction.add( "NW" );
        direction.add( "E" );
        direction.add( "W" );
        direction.add( "N" );
        direction.add( "S" );
    }

    private String getRandomDirection() {
        return direction.get( randInt( 0, direction.size() - 1 ) );
    }

    private void generateStreetName(String streets) {
        try {
            if ( streets != null ) {
                // Use a set in order to remove duplicate dictionary words.
                Set<String> sNames = new HashSet<>();
                for (String s : streets.split( System.getProperty( "line.separator" ) )) {
                    sNames.add( s.trim() );
                }
                streetNames = new ArrayList<>( sNames );
            } else {
                System.out.println( "streets are null!" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRandomStreetname() {
        return streetNames.get( randInt( 0, streetNames.size() - 1 ) ).trim();
    }

    private void generateStreetType() {
        streetType = new ArrayList<>( 11 );
        streetType.add( "Street" );
        streetType.add( "boulevard" );
        streetType.add( "drive" );
        streetType.add( "avenue" );
        streetType.add( "place" );
        streetType.add( "lane" );
        streetType.add( "trail" );
        streetType.add( "Way" );
        streetType.add( "Turnpike" );
        streetType.add( "Alley" );
        streetType.add( "Parkway" );
    }

    private String getRandomStreetType() {
        return streetType.get( randInt( 0, streetType.size() - 1 ) );
    }

    private void generateCityAndState(String cities) {
        if ( cities != null ) {
            String[] cityArray = cities.split( System.getProperty( "line.separator" ) );
            cityState = new ArrayList<>( cityArray.length );
            for (String c : cityArray)
                cityState.add( c.trim() );
        } else
            System.out.println( "cities are null!" );
    }

    private String[] getRandomCityAndState() {
        return cityState.get( randInt( 0, cityState.size() - 1 ) ).split( "," );
    }

    private int getRandomZip() {
        return randInt( 11111, 99999 );
    }

    private int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt( min, max + 1 );
    }

    private String generatePhone() {
        StringBuilder sb = new StringBuilder();
        sb.append( randInt( 1, 9 ) );
        for (int i = 0; i < 8; i++)
            sb.append( randInt( 0, 9 ) );

        return sb.toString();
    }

    public String getRandomStreet() {
        String street = getRandomStreetNumber() + " " + getRandomDirection() + " " +
            getRandomStreetname() + " " + getRandomStreetType();
        while (street.length() > 30) {
            street = street.substring( 0, street.lastIndexOf( ' ' ) );
        }

        return street;
    }

    public Address generateAddress() {
        if ( cityState.size() > 0 && streetNames.size() > 0 ) {
            String[] city = getRandomCityAndState();
            return new Address( getRandomStreet(), null, city[0], city[1], "USA", String.valueOf( getRandomZip() ),
                generatePhone() );
        }
        return null;
    }



}
