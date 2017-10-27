package org.jboss.perf.webapp;

import org.jboss.perf.common.Vehicle;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by johara on 19/10/17.
 */
@Path("/vehicle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleEndpoint {

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPolicyHolderVehicleByEmail(@Valid Vehicle vehicle){
        System.out.println("Adding Vehicle: " + vehicle.toString());
    }
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getResponse(){
        return "Get ran OK!";
    }

}
