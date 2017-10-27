package org.jboss.perf.client.util;

import java.util.Random;

/**
 * Created by johara on 20/10/17.
 */
public class RandNum {
    double _100_PERCENTAGE = 1.0;

    private static final char[] alpha = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
        'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    private final Random r;

    public RandNum() {
        r = new Random();
    }

    public RandNum(final long seed) {
        r = new Random(seed);
    }

    public long nextLong() {
        return r.nextLong();
    }

    public int nextInt(int i) {
        return r.nextInt(i);
    }

    /**
     * Selects a random number uniformly distributed between x and y, inclusively, with a mean of (x+y)/2.
     */
    public int random(int x, int y) {
        int n = r.nextInt();
        return x + Math.abs(n % (y - x + 1));
    }

    /**
     * Selects a long random number uniformly distributed between x and y, inclusively, with a mean of (x+y)/2.
     */
    public long lrandom(long x, long y) {
        long n = r.nextLong();
        return x + Math.abs(n % (y - x + 1));
    }

    /**
     * @see #lrandom(long, long), but returns double.
     */
    public double drandom(double x, double y) {
        return (x + (r.nextDouble() * (y - x)));
    }

    /**
     * Generates a random string of alphanumeric characters of random length of mininum x, maximum y and mean (x+y)/2.
     */
    public String makeAString(int x, int y) {
        return makeAString(x, y, true);
    }

    public String makeAString(int x, int y, boolean caseSensitive) {
        final int len; /* len of string */
        if (x == y) {
            len = x;
        } else {
            len = random(x, y);
        }
        char[] buffer = new char[len];
        for (int i = 0; i < len; i++) {
            int j = Math.abs(random(0, caseSensitive ? alpha.length-1 : alpha.length - 27));
            buffer[i] = alpha[j];
        }
        return new String(buffer);
    }

    /**
     * Generates a random string of numeric characters of random length of mininum x, maximum y and mean (x+y)/2.
     */
    public String makeNString(int x, int y) {
        final int len;
        if (x == y) {
            len = x;
        } else {
            len = random(x, y);
        }
        final StringBuffer str = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            str.append(random(0, 9));
        }
        return str.toString();
    }

    /**
     * Decides randomly if event is occuring depending on the probability percentage.
     * @param percentage that event occurrs
     * @return boolean value if the event occurs
     */
    public boolean isOccurring(double percentage) {
        return drandom(0, _100_PERCENTAGE) <= percentage;
    }
}
