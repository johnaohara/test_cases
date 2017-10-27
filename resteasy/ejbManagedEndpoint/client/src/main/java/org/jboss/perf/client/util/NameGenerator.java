package org.jboss.perf.client.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by johara on 20/10/17.
 */
public class NameGenerator extends Generator {
    private static final Object LOCK = new Object();
    private static volatile NameGenerator INSTANCE;

    private List<String> fname;
    private List<String> lname;
    private List<String> domainNames = new ArrayList<>();

    private NameGenerator(String female, String male, String last) {
        generateNames(
            female != null ? readFile(female) : readFileInJar("female_first.txt"),
            male != null ? readFile(male) : readFileInJar("male_first.txt"),
            last != null ? readFile(last) : readFileInJar("last.txt"));
    }

    public static NameGenerator getInstance() {
        return getInstance(null, null, null);
    }

    public static NameGenerator getInstance(String female, String male, String last) {
        if(INSTANCE == null) {
            synchronized (LOCK) {
                if(INSTANCE == null)
                    INSTANCE = new NameGenerator(female, male, last);
            }
        }
        return INSTANCE;
    }

    private void generateNames(String fNames, String mNames, String last) {
        try {
            // Use a set in order to remove duplicate first names for unisex names.
            Set<String> firstNames = new HashSet<>();
            for(String f : fNames.split(System.getProperty("line.separator"))) {
                firstNames.add(capitalizeWord(f));
            }

            for(String m : mNames.split(System.getProperty("line.separator"))) {
                firstNames.add(capitalizeWord(m));
            }

            fname = new ArrayList<>(firstNames);

            String[] lastNameArray = last.split("\r\n");
            lname = new ArrayList<>(lastNameArray.length);
            for(String l : lastNameArray) {
                lname.add(capitalizeWord(l));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String capitalizeWord(String name) {
        return name.charAt(0)+ name.toLowerCase().substring(1);
    }

    public String getRandomFirstName() {
        return fname.get(randInt(0, fname.size()-1));
    }

    public String getRandomLastName() {
        return lname.get(randInt(0, lname.size()-1));
    }

    private String getRandomDomainName() {
        if(domainNames.size() == 0)
            generateDomainNames();

        return domainNames.get(randInt(0, domainNames.size()-1));
    }

    public String getRandomEmail(String firstName, String lastName, int suffix) {
        return firstName+"."+lastName+"_"+suffix+"@"+getRandomDomainName();
    }

    private void generateDomainNames() {
        domainNames.add("spec.org");
        domainNames.add("google.com");
        domainNames.add("apple.com");
        domainNames.add("ibm.com");
        domainNames.add("intel.com");
        domainNames.add("oracle.com");
        domainNames.add("redhat.com");
        domainNames.add("ebay.com");
        domainNames.add("facebook.com");
        domainNames.add("hpe.com");
        domainNames.add("msn.com");
        domainNames.add("microsoft.com");
        domainNames.add("hotmail.com");
        domainNames.add("aol.com");
        domainNames.add("yahoo.com");
        domainNames.add("gmail.com");
        domainNames.add("outlook.com");
        domainNames.add("hushmail.com");
        domainNames.add("mail.com");
    }


    private int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}