package main.java.se.bumaklion.myrecipes.resources;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

import main.java.se.bumaklion.myrecipes.domain.Recipe;
import main.java.se.bumaklion.myrecipes.domain.RecipeIngredient;
import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.service.BumService;
import main.java.se.bumaklion.myrecipes.service.IngredientService;
import main.java.se.bumaklion.myrecipes.service.MeasurementService;
import main.java.se.bumaklion.myrecipes.service.RecipeIngredientService;
import main.java.se.bumaklion.myrecipes.service.RecipeService;
import main.java.se.bumaklion.myrecipes.service.UserService;
import main.java.se.bumaklion.myrecipes.util.BumPojoUtil;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonParser;
import main.java.se.bumaklion.myrecipes.util.logging.BumLogger;

@Path("/users/{userId}/recipes/{recipeId}/recipeIngredients")
public class RecipeIngredientResource extends BumResource<RecipeIngredient> {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInstructions(@Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeId") String recipeUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

		Recipe recipe = new RecipeService().getByUuid(recipeUuid);
		if (recipe == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a recipe with the id: <" + recipeUuid + ">.");

		return getResponse(Status.OK, getJson(recipe.getRecipeIngredients(), "RecipeIngredients", uriInfo));
	}

	@GET
	@Path("{recipeIngredientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInstruction(@Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeId") String recipeUuid, @PathParam("recipeIngredientId") String recipeIngredientUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

		Recipe recipe = new RecipeService().getByUuid(recipeUuid);
		if (recipe == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a recipe with the id: <" + recipeUuid + ">.");

		RecipeIngredient ri = new RecipeIngredientService().getByUuid(recipeIngredientUuid);
		if (ri == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a recipeIngredient with the id: <" + recipeIngredientUuid + ">.");

		return getResponse(Status.OK, getJson(ri, uriInfo));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRecipeIngredient(String json, @Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeId") String recipeUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getResponse(Status.BAD_REQUEST, "Cant' find a user with the id: <" + userUuid + ">.");

		Recipe recipe = new RecipeService().getByUuid(recipeUuid);
		if (recipe == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a recipe with the id: <" + recipeUuid + ">.");

		RecipeIngredient ri = null;
		try {
			ri = new BumJsonParser().create(RecipeIngredient.class, json);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | IOException | IntrospectionException | ParseException e) {
			BumLogger.error(e);
			return getBadJsonResponse(json);
		}

		if (ri.getAmount() <= 0.0)
			return getResponse(Status.BAD_REQUEST, "Why would you want negative amounts? " + ri.getAmount());
		if (!BumPojoUtil.isSavedBefore(ri.getMeasurement()))
			return getResponse(Status.BAD_REQUEST, "The RecipeIngredient needs a persistent Measurement!");
		if (!BumPojoUtil.isSavedBefore(ri.getIngredient()))
			return getResponse(Status.BAD_REQUEST, "The RecipeIngredient needs a persistent Ingridient!");

		ri.setRecipe(recipe);
		//reload the measurement and ingredient since the client might just have sent the UUID of these 
		//(this seems like bad design, or is it just the nature of mixing REST style and relational DB?)
		ri.setMeasurement(new MeasurementService().getByUuid(ri.getMeasurement().getUuid()));
		ri.setIngredient(new IngredientService().getByUuid(ri.getIngredient().getUuid()));

		ri = new RecipeIngredientService().saveOrUpdate(ri);

		return getResponse(Status.CREATED, getJson(ri, uriInfo));
	}

	@POST
	@Path("{recipeIngredientId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateInstruction(String json, @Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeId") String recipeUuid, @PathParam("recipeIngredientId") String recipeIngredientUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getResponse(Status.BAD_REQUEST, "Cant' find a user with the id: <" + userUuid + ">.");

		Recipe recipe = new RecipeService().getByUuid(recipeUuid);
		if (recipe == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a recipe with the id: <" + recipeUuid + ">.");

		RecipeIngredientService service = new RecipeIngredientService();
		RecipeIngredient ri = service.getByUuid(recipeIngredientUuid);
		if (ri == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a RecipeIngredient with the id: <" + recipeIngredientUuid + ">.");

		try {
			ri = new BumJsonParser().update(ri, json);
		} catch (ParseException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | IntrospectionException e) {
			BumLogger.error(e);
			return getBadJsonResponse(json);
		}

		ri.setRecipe(recipe);
		//reload the measurement and ingredient since the client might just have sent the UUID of these 
		//(this seems like bad design, or is it just the nature of mixing REST style and relational DB?)
		ri.setMeasurement(new MeasurementService().getByUuid(ri.getMeasurement().getUuid()));
		ri.setIngredient(new IngredientService().getByUuid(ri.getIngredient().getUuid()));

		ri = new RecipeIngredientService().saveOrUpdate(ri);

		return getResponse(Status.OK, getJson(ri, uriInfo));
	}

	@Override
	protected BumService<RecipeIngredient> getService() {
		return new RecipeIngredientService();
	}

	@Override
	protected Class<RecipeIngredient> getClazz() {
		return RecipeIngredient.class;
	}

}
