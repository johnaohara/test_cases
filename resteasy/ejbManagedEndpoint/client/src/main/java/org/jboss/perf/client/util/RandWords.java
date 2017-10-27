package org.jboss.perf.client.util;

import org.jboss.perf.common.Gender;

/**
 * Created by johara on 20/10/17.
 */
public class RandWords {
    private static final String[] countries = {"ABW", "AFG", "AGO", "AIA", "ALA", "ALB", "AND", "ARE", "ARG", "ARM", "ASM",
        "ATA", "ATF", "ATG", "AUS", "AUT", "AZE", "BDI", "BEL", "BEN", "BES", "BFA", "BGD", "BGR", "BHR", "BHS", "BIH", "BLM",
        "BLR", "BLZ", "BMU", "BOL", "BRA", "BRB", "BRN", "BTN", "BVT", "BWA", "CAF", "CAN", "CCK", "CHE", "CHL", "CHN", "CIV",
        "CMR", "COD", "COG", "COK", "COL", "COM", "CPV", "CRI", "CUB", "CUW", "CXR", "CYM", "CYP", "CZE", "DEU", "DJI", "DMA",
        "DNK", "DOM", "DZA", "ECU", "EGY", "ERI", "ESH", "ESP", "EST", "ETH", "FIN", "FJI", "FLK", "FRA", "FRO", "FSM", "GAB",
        "GBR", "GEO", "GGY", "GHA", "GIB", "GIN", "GLP", "GMB", "GNB", "GNQ", "GRC", "GRD", "GRL", "GTM", "GUF", "GUM", "GUY",
        "HKG", "HMD", "HND", "HRV", "HTI", "HUN", "IDN", "IMN", "IND", "IOT", "IRL", "IRN", "IRQ", "ISL", "ISR", "ITA", "JAM",
        "JEY", "JOR", "JPN", "KAZ", "KEN", "KGZ", "KHM", "KIR", "KNA", "KOR", "KWT", "LAO", "LBN", "LBR", "LBY", "LCA", "LIE",
        "LKA", "LSO", "LTU", "LUX", "LVA", "MAC", "MAF", "MAR", "MCO", "MDA", "MDG", "MDV", "MEX", "MHL", "MKD", "MLI", "MLT",
        "MMR", "MNE", "MNG", "MNP", "MOZ", "MRT", "MSR", "MTQ", "MUS", "MWI", "MYS", "MYT", "NAM", "NCL", "NER", "NFK", "NGA",
        "NIC", "NIU", "NLD", "NOR", "NPL", "NRU", "NZL", "OMN", "PAK", "PAN", "PCN", "PER", "PHL", "PLW", "PNG", "POL", "PRI",
        "PRK", "PRT", "PRY", "PSE", "PYF", "QAT", "REU", "ROU", "RUS", "RWA", "SAU", "SDN", "SEN", "SGP", "SGS", "SHN", "SJM",
        "SLB", "SLE", "SLV", "SMR", "SOM", "SPM", "SRB", "SSD", "STP", "SUR", "SVK", "SVN", "SWE", "SWZ", "SXM", "SYC", "SYR",
        "TCA", "TCD", "TGO", "THA", "TJK", "TKL", "TKM", "TLS", "TON", "TTO", "TUN", "TUR", "TUV", "TWN", "TZA", "UGA", "UKR",
        "UMI", "URY", "USA", "UZB", "VAT", "VCT", "VEN", "VGB", "VIR", "VNM", "VUT", "WLF", "WSM", "YEM", "ZAF", "ZMB", "ZWE"};


    private static final Gender[] genders = {Gender.MALE,Gender.FEMALE};
    private final RandNum r;

    public RandWords() {
        r = new RandNum();
    }

    public RandWords(final long seed) {
        r = new RandNum(seed);
    }

    /**
     * Pick a country from the internal data array set.
     * @return A country string.
     */
    public String getNextCountry() {
        int idx = r.random(0, countries.length - 1);
        return countries[idx];
    }

    /**
     * Pick a gender from the internal data array set.
     * @return A gender string.
     */
    public Gender getNextGender() {
        int idx = r.random(0, genders.length - 1);
        return genders[idx];
    }

    /**
     * Generates a random date in the format of dd-mm-yyyy
     * @return A string with a generated date.
     */
    public String getNextDate() {
        StringBuilder sb = new StringBuilder();
        int month = r.random(1, 12);
        int day = 1;
        if(month == 2)
            day = r.random(1, 28);
        else
            day = r.random(1, 30);
        sb.append(day );     // day
        sb.append("-");
        sb.append(month);      // month
        sb.append("-");
        sb.append(r.random(1920, 2010)); // year
        return sb.toString();
    }

    /**
     * Generates a random string of numeric characters for a phone number.
     * @return A string containing a phone number.
     */
    public String getNextPhoneNumber(){
        return r.makeNString(10, 10);
    }

    /**
     * Generates a random string of characters to be used for
     * a password in the benchmark run
     * @return A string for a password.
     */
    public String getNextPassword(){
        return r.makeAString(8, 20);
    }

    /**
     * Generates a random string of numeric characters.
     * RandNum is called to generate the String.
     * @param x The minimum length of string to create
     * @param y The maximum length of string to create
     * @return A random string of numeric characters
     * of random length of minimum x, maximum y and mean (x+y)/2.
     * @see org.spec.jent.common.util.RandNum
     */
    public String makeNString(int x, int y) {
        return r.makeNString(x, y);
    }

    public String getNextVin(int suffix) {
        String randPrefix = r.makeAString(11, 11, false);
        StringBuilder builder = new StringBuilder(17);
        builder.append(randPrefix);
        String randSuffix = Integer.toString(suffix);
        for (int i = 6 - randSuffix.length(); i > 0; --i) {
            builder.append("0");
        }
        builder.append(randSuffix);
        return builder.toString();
    }



}
