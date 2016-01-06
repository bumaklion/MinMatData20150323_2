package main.java.se.bumaklion.myrecipes;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by bumaklion on 2015-03-24.
 */
public class WebApp extends ResourceConfig {

    public WebApp() {
        //tell jersey where to find resources
        packages("main.java.se.bumaklion.myrecipes.resources");

        //enable CORS
        register(CorsResponseFilter.class);

        //enable BASIC auth
        register(RequestFilter.class);
    }

}
