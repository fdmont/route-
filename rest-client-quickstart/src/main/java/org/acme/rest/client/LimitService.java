package org.acme.rest.client;


import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@RegisterRestClient
public interface LimitService {

    @GET
    @Path("/hello")
    @Produces("application/json")
    String get();


    @GET
    @Path("/uni")
    @Produces("application/json")
    Uni<String> uni();


    @GET
    @Path("/multi")
    @Consumes("application/json")
    //@Produces(MediaType.SERVER_SENT_EVENTS)
    CompletionStage<String> multi();


    @GET
    @Path("/multilimit")
    @Produces("application/json")
  //  @Produces(MediaType.SERVER_SENT_EVENTS)
    CompletionStage<Object> multil();



    @GET
    @Path("/name/{name}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    CompletionStage<Set<Country>> getByNameAsync(@PathParam String name);

    @GET
    @Path("/name/{name}")
    @Produces("application/json")
    Uni<Set<Country>> getByNameAsUni(@PathParam String name);
}
