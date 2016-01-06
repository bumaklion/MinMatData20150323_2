package main.java.se.bumaklion.myrecipes.resources;

import static main.java.se.bumaklion.myrecipes.util.logging.BumLogger.error;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.service.BumService;
import main.java.se.bumaklion.myrecipes.service.UserService;
import main.java.se.bumaklion.myrecipes.util.Args;
import main.java.se.bumaklion.myrecipes.util.PasswordHash;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonParser;
import main.java.se.bumaklion.myrecipes.util.logging.BumLogger;

/**
 * @author Olle
 */
@Path("users")
public class UserResource extends BumResource<User> {

	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@Context UriInfo uriInfo, @PathParam("userId") String uuid) {
		User user = new UserService().getByUuid(uuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id: <" + uuid + ">.");

		return getResponse(Status.OK, getJson(user, uriInfo));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(String json, @Context UriInfo uriInfo) {
		User user = null;
		try {
			user = new BumJsonParser().create(User.class, json);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | IOException | IntrospectionException | ParseException e1) {
			error(e1);
			return getBadJsonResponse(json);
		}

		Args.notNull(user.getLogin(), "login");
		Args.notNull(user.getPassword(), "password");
		try {
			user.setPasswordHash(PasswordHash.createHash(user.getPassword()));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			BumLogger.error(e);
			return getErrorResponse(Status.INTERNAL_SERVER_ERROR, "Faild to generate passsword hash.");
		}

		user = new UserService().saveOrUpdate(user);
		return getResponse(Status.CREATED, getJson(user, uriInfo));
	}

	@POST
	@Path("{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatetUser(String json, @Context UriInfo uriInfo, @PathParam("userId") String uuid) {
		UserService userService = new UserService();
		User user = userService.getByUuid(uuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find that user!");

		try {
			user = new BumJsonParser().update(user, json);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | IntrospectionException | ParseException e) {
			error(e);
			return getBadJsonResponse(json);
		}

		user = userService.saveOrUpdate(user);
		return getResponse(Status.OK, getJson(user, uriInfo));
	}

	@Override
	protected BumService<User> getService() {
		return new UserService();
	}

	@Override
	protected Class<User> getClazz() {
		return User.class;
	}
}
