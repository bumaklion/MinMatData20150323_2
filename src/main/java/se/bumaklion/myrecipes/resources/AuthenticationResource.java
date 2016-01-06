package main.java.se.bumaklion.myrecipes.resources;

import com.google.gson.Gson;
import main.java.se.bumaklion.myrecipes.service.UserService;
import main.java.se.bumaklion.myrecipes.util.Args;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * This is basically just to get uuid of a user since all calls are authenticated.
 * <p>
 * Created by bumaklion on 2015-03-25.
 */
@Path("authentication")
public class AuthenticationResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(String json, @Context UriInfo uriInfo) {
        AuthenticationData data = new Gson().fromJson(json, AuthenticationData.class);
        Args.notNull(data.username, "username");

        String uuid = new UserService().getUserByLogin(data.username).getUuid();
        return Response.status(Response.Status.OK).entity("{\"uuid\":\"" + uuid + "\"}").build();
    }

    public class AuthenticationData {
        private String username;
    }

}
