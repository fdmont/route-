package org.acme.quickstart.reactiveRoutes;


import com.google.gson.Gson;
import io.quarkus.vertx.web.*;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.acme.quickstart.model.Auditory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Method;

@ApplicationScoped
public class MyDeclarativeRoutes {


    // neither path nor regex is set - match a path derived from the method name
    @Route(methods = HttpMethod.GET)
    void hello(RoutingContext rc) {
        rc.response().end("hello");
    }

    @Route(path = "/world")
    String helloWorld() {
        return "Hello world!";
    }

    @Route(path = "/greetings", methods = HttpMethod.GET)
    void greetings(RoutingExchange ex) {
            ex.ok("hello " + ex.getParam("name").orElse("world"));
    }

    //example bloqueante
    @Route(methods = HttpMethod.POST, path = "/post", type = Route.HandlerType.BLOCKING)
    public void blocking(RoutingContext rc) {
        // ...
    }

    @Route(methods= HttpMethod.GET, path = "/limite")
    public void limit(RoutingExchange re){
        re.ok("response " +re.getParam("device").orElse("no se indico device"));
    }

    @Route(methods= HttpMethod.GET, path = "/headers")
    String helloFromHeader(@Header("My-Header") String header) { //el header entra por el body se retorna el valor, puede ser cualquiera
        return header;                                      //podria validar el header segun su valor para retornar una respuesta si o no si el header es valido
    }

    @Route(path = "/multi")
    Multi<String> helloMul(RoutingContext context) {
       // return ReactiveRoutes.asJsonArray(Multi.createFrom().items("Hello world!","otro messages"));
        return ReactiveRoutes.asEventStream(Multi.createFrom().items("Hello world!","otro messages"));
    }

    @Route(path = "/uni")
    Uni<String> helloUni(RoutingContext context) {
        return Uni.createFrom().item("Hello world!");
    }

    @Route(methods= HttpMethod.GET, path = "/hello")
    public String hello() {
        return "hello cucho";
    }


    @Route(path = "/multilimit")
    Multi<Auditory> helloMuli(RoutingContext context) {
        return ReactiveRoutes.asEventStream(Multi.createFrom().items(new Auditory(1,2,"auditory 1"), new Auditory(2,2,"auditory 2")));
       // return ReactiveRoutes.asJsonArray(Multi.createFrom().items(new Auditory(1,2,"auditory 1"), new Auditory(2,2,"auditory 2")));

    }

    private Multi<Object> convertToJsonObjec(Object item) {
        Gson gson = new Gson();
        String jsonRepresentation = gson.toJson(item);
        return  Multi.createFrom().item(jsonRepresentation);
    }
}


@RouteBase(path = "simple", produces = "text/plain")
class SimpleRoutes {

    @Route(path = "ping") // the final path is /simple/ping
    void ping(RoutingContext rc) {
        rc.response().end("pong");
    }

}