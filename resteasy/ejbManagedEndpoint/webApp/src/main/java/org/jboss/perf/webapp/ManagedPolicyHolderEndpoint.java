package org.jboss.perf.webapp;

import org.jboss.perf.common.PolicyHolder;

import javax.ejb.Stateless;
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
@Stateless
@Path("/policyholder")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ManagedPolicyHolderEndpoint {

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPolicyHolderVehicleByEmail(@Valid PolicyHolder policyHolder){
        System.out.println("Adding PolicyHolder: " + policyHolder.toString());
    }
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getResponse(){
        return "Get ran OK!";
    }

}
