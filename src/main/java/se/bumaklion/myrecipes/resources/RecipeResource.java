package main.java.se.bumaklion.myrecipes.resources;

import main.java.se.bumaklion.myrecipes.domain.Recipe;
import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.service.BumService;
import main.java.se.bumaklion.myrecipes.service.RecipeService;
import main.java.se.bumaklion.myrecipes.service.UserService;
import main.java.se.bumaklion.myrecipes.util.Strings;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonParser;
import main.java.se.bumaklion.myrecipes.util.logging.BumLogger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@Path("/users/{userId}/recipes")
public class RecipeResource extends BumResource<Recipe> {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRecipe(String json, @Context UriInfo uriInfo, @PathParam("userId") String userUuid) {
        User user = new UserService().getByUuid(userUuid);
        if (user == null)
            return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

        Recipe recipe = null;
        try {
            recipe = new BumJsonParser().create(Recipe.class, json);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | IOException | IntrospectionException | ParseException e) {
            BumLogger.error(e);
            return getBadJsonResponse(json);
        }

        recipe.setChef(user);
        if (Strings.isEmpty(recipe.getTitle()))
            return getErrorResponse(Status.BAD_REQUEST, "The recipe needs to have a title!");

        recipe = new RecipeService().saveOrUpdate(recipe);

        return getResponse(Status.CREATED, getJson(recipe, uriInfo));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{recipeUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRecipe(String json, @Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeUuid") String recipeUuid) {
        User user = new UserService().getByUuid(userUuid);
        if (user == null)
            return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

        RecipeService service = new RecipeService();
        Recipe recipe = service.getByUuid(recipeUuid);
        if (recipe == null)
            return getErrorResponse(Status.BAD_REQUEST, "Can't find recipe with id <" + recipeUuid + ">.");

        try {
            recipe = new BumJsonParser().update(recipe, json);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | IntrospectionException | ParseException e) {
            BumLogger.error(e);
            return getBadJsonResponse(json);
        }

        recipe.setChef(user);
        if (Strings.isEmpty(recipe.getTitle()))
            return getErrorResponse(Status.BAD_REQUEST, "The recipe needs to have a title!");

        recipe = service.saveOrUpdate(recipe);

        return getResponse(Status.OK, getJson(recipe, uriInfo));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipes(@Context UriInfo uriInfo, @PathParam("userId") String userUuid) {
        User user = new UserService().getByUuid(userUuid);
        if (user == null)
            return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

        List<Recipe> recipes = new RecipeService().getRecipes(user);
        String json = getJson(recipes, "Recipes", uriInfo);

        return getResponse(Status.OK, json);
    }

    @GET
    @Path("{recipeUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecificRecipe(@Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeUuid") String recipeUuid) {
        User user = new UserService().getByUuid(userUuid);
        if (user == null)
            return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

        Recipe recipe = new RecipeService().getByUuid(recipeUuid);
        if (recipe == null)
            return getErrorResponse(Status.BAD_REQUEST, "Can't find recipe with id <" + recipeUuid + ">.");
        return getResponse(Status.OK, getJson(recipe, uriInfo));
    }

    @Override
    protected BumService<Recipe> getService() {
        return new RecipeService();
    }

    @Override
    protected Class<Recipe> getClazz() {
        return Recipe.class;
    }
}
