package main.java.se.bumaklion.myrecipes.resources;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

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

import main.java.se.bumaklion.myrecipes.domain.Ingredient;
import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.service.BumService;
import main.java.se.bumaklion.myrecipes.service.IngredientService;
import main.java.se.bumaklion.myrecipes.service.UserService;
import main.java.se.bumaklion.myrecipes.util.Strings;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonParser;
import main.java.se.bumaklion.myrecipes.util.logging.BumLogger;

@Path("/users/{userId}/ingredients")
public class IngredientResource extends BumResource<Ingredient> {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createIngredient(String json, @Context UriInfo uriInfo, @PathParam("userId") String userUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

		Ingredient ingredient = null;
		try {
			ingredient = new BumJsonParser().create(Ingredient.class, json);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | IOException | IntrospectionException | ParseException e) {
			BumLogger.error(e);
			return getBadJsonResponse(json);
		}

		ingredient.setUser(user);
		if (Strings.isEmpty(ingredient.getName()))
			return getErrorResponse(Status.BAD_REQUEST, "The ingredients needs to have a name!");

		ingredient = new IngredientService().saveOrUpdate(ingredient);

		return getResponse(Status.CREATED, getJson(ingredient, uriInfo));
	}

	@POST
	@Path("{ingredientId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateIngredient(String json, @Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("ingredientId") String ingredientUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

		IngredientService service = new IngredientService();
		Ingredient ingredient = service.getByUuid(ingredientUuid);
		if (ingredient == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find ingredient with id <" + ingredientUuid + ">.");
		try {
			ingredient = new BumJsonParser().update(ingredient, json);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | IntrospectionException | ParseException e) {
			BumLogger.error(e);
			return getBadJsonResponse(json);
		}

		ingredient.setUser(user);
		if (Strings.isEmpty(ingredient.getName()))
			return getErrorResponse(Status.BAD_REQUEST, "The ingredients needs to have a name!");

		ingredient = new IngredientService().saveOrUpdate(ingredient);

		return getResponse(Status.OK, getJson(ingredient, uriInfo));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIngredients(@Context UriInfo uriInfo, @PathParam("userId") String userUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

		List<Ingredient> ingredients = new IngredientService().getIngridients(user);
		String json = getJson(ingredients, "Ingredients", uriInfo);

		return getResponse(Status.OK, json);
	}

	@GET
	@Path("{ingredientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecificRecipe(@Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("ingredientId") String ingredientUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

		Ingredient ingredient = new IngredientService().getByUuid(ingredientUuid);
		if (ingredient == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find ingredient with id <" + ingredientUuid + ">.");

		return getResponse(Status.OK, getJson(ingredient, uriInfo));
	}

	@Override
	protected BumService<Ingredient> getService() {
		return new IngredientService();
	}

	@Override
	protected Class<Ingredient> getClazz() {
		return Ingredient.class;
	}

}
