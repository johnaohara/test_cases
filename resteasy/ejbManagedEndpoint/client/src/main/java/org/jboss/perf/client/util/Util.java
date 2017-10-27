package org.jboss.perf.client.util;

import java.util.List;

/**
 * Created by johara on 20/10/17.
 */
public class Util {
    public static final RandWords randw = new RandWords();
    public static final RandNum randn = new RandNum();

    public static  <T> T rand(List<T> list) {
        return list.get((list.size()==1 ? 0 : randn.random(0, list.size() - 1)));
    }

}
