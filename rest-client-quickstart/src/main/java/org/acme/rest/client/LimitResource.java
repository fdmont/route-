package org.acme.rest.client;


import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/limit")
public class LimitResource {

    @Inject
    @RestClient
    LimitService countriesService;

    @GET
    @Path("/hello")
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {
        return countriesService.get();
    }

    @GET
    @Path("/uni")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<String> uni() {
        return countriesService.uni();
    }

  
    @GET
    @Path("/multi")
    @Consumes(MediaType.APPLICATION_JSON)
   // @Produces(MediaType.SERVER_SENT_EVENTS)
    public  CompletionStage<String> multi(){
        return countriesService.multi();
    }

    @GET
    @Path("/multilimit")
    @Consumes(MediaType.APPLICATION_JSON)
    
    public  CompletionStage<Object> multil(){
        return countriesService.multil();
    }

}
