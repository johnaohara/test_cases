package org.jboss.perf.webapp;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by johara on 19/10/17.
 */
@ApplicationPath("/rest")
public class RestApplication extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<>( Arrays.asList(
            VehicleEndpoint.class,
            PolicyHolderEndpoint.class
        ) );
    }
}
