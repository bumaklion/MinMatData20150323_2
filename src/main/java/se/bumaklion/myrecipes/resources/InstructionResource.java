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

import main.java.se.bumaklion.myrecipes.domain.Instruction;
import main.java.se.bumaklion.myrecipes.domain.Recipe;
import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.service.BumService;
import main.java.se.bumaklion.myrecipes.service.InstructionService;
import main.java.se.bumaklion.myrecipes.service.RecipeService;
import main.java.se.bumaklion.myrecipes.service.UserService;
import main.java.se.bumaklion.myrecipes.util.Args;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonParser;
import main.java.se.bumaklion.myrecipes.util.logging.BumLogger;

@Path("/users/{userId}/recipes/{recipeId}/instructions")
public class InstructionResource extends BumResource<Instruction> {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInstructions(@Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeId") String recipeUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

		Recipe recipe = new RecipeService().getByUuid(recipeUuid);
		if (recipe == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a recipe with the id: <" + recipeUuid + ">.");

		return getResponse(Status.OK, getJson(recipe.getInstructions(), "Instructions", uriInfo));
	}

	@GET
	@Path("{instructionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInstruction(@Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeId") String recipeUuid, @PathParam("instructionId") String instructionUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getErrorResponse(Status.BAD_REQUEST, "Can't find user with id <" + userUuid + ">.");

		Recipe recipe = new RecipeService().getByUuid(recipeUuid);
		if (recipe == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a recipe with the id: <" + recipeUuid + ">.");

		Instruction instruction = new InstructionService().getByUuid(instructionUuid);
		if (instruction == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a instruction with the id: <" + instructionUuid + ">.");

		return getResponse(Status.OK, getJson(instruction, uriInfo));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createInstruction(String json, @Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeId") String recipeUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getResponse(Status.BAD_REQUEST, "Cant' find a user with the id: <" + userUuid + ">.");

		Recipe recipe = new RecipeService().getByUuid(recipeUuid);
		if (recipe == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a recipe with the id: <" + recipeUuid + ">.");

		Instruction instruction = null;
		try {
			instruction = new BumJsonParser().create(Instruction.class, json);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | IOException | IntrospectionException | ParseException e) {
			BumLogger.error(e);
			return getBadJsonResponse(json);
		}

		Args.notEmpty(instruction.getInstructionText(), "text");
		instruction.setRecipe(recipe);

		instruction = new InstructionService().saveOrUpdate(instruction);

		return getResponse(Status.CREATED, getJson(instruction, uriInfo));
	}

	@POST
	@Path("{instructionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateInstruction(String json, @Context UriInfo uriInfo, @PathParam("userId") String userUuid, @PathParam("recipeId") String recipeUuid, @PathParam("instructionId") String instructionUuid) {
		User user = new UserService().getByUuid(userUuid);
		if (user == null)
			return getResponse(Status.BAD_REQUEST, "Cant' find a user with the id: <" + userUuid + ">.");

		Recipe recipe = new RecipeService().getByUuid(recipeUuid);
		if (recipe == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a recipe with the id: <" + recipeUuid + ">.");

		InstructionService instructionService = new InstructionService();
		Instruction instruction = instructionService.getByUuid(instructionUuid);
		if (instruction == null)
			return getResponse(Status.BAD_REQUEST, "Can't find a instruction with the id: <" + instructionUuid + ">.");

		try {
			instruction = new BumJsonParser().update(instruction, json);
		} catch (ParseException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException | IntrospectionException e) {
			BumLogger.error(e);
			return getBadJsonResponse(json);
		}

		Args.notEmpty(instruction.getInstructionText(), "text");
		instruction.setRecipe(recipe);

		instruction = new InstructionService().saveOrUpdate(instruction);

		return getResponse(Status.OK, getJson(instruction, uriInfo));
	}

	@Override
	protected BumService<Instruction> getService() {
		return new InstructionService();
	}

	@Override
	protected Class<Instruction> getClazz() {
		return Instruction.class;
	}

}
