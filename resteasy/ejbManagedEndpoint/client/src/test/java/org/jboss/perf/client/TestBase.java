package org.jboss.perf.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Assert;
import org.junit.Before;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class TestBase {
    private String GET_URL = "";
    private String POST_URL = "";
    private ResteasyClient client ;
    private ResteasyWebTarget postTarget;
    private ResteasyWebTarget getTarget;

    public TestBase(String GET_URL, String POST_URL) {
        this.GET_URL = GET_URL;
        this.POST_URL = POST_URL;
    }

    @Before
    public void setupTest(){
        client = createJAXRSClient();
        if (POST_URL != null) postTarget = client.target( POST_URL );
        if (GET_URL != null) getTarget = client.target( GET_URL );
    }

    protected ResteasyClient createJAXRSClient() {
        return new ResteasyClientBuilder().build();
    }

    protected Response executePost(Object entity){
        return postTarget
            .request( MediaType.APPLICATION_JSON)
            .accept( MediaType.APPLICATION_JSON )
            .post(
                Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE));
    }

    protected Response executeGet(){
        return getTarget.request().get();
    }

    protected void validateResponse(int expected, Response response){
        try {
            Assert.assertEquals(expected, response.getStatus());
        }
        finally {
            response.close();
        }

    }

}
