package main.java.se.bumaklion.myrecipes.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;


import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.service.BumService;
import main.java.se.bumaklion.myrecipes.util.QueryParameters;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonProducer;
import main.java.se.bumaklion.myrecipes.util.json.help.JsonArray;
import main.java.se.bumaklion.myrecipes.util.json.help.JsonObject;

/**
 * @author Olle
 */
public abstract class BumResource<T extends BumPojo> {

	protected String getJson(Collection<T> pojos, String name, UriInfo uriInfo) {
		JsonObject mainJson = new JsonObject();
		JsonArray entries = new JsonArray();

		for (T pojo : pojos)
			entries.add(new BumJsonProducer().getJson(pojo, getExpandParameters(uriInfo)));

		mainJson.add(name, entries);
		String json = mainJson.toString();
/*
		System.out.println(json);
		
		json = json.replace("{\"Recipes\":", "");
		json = json.substring(0, json.length() - 1);

		System.out.println(json);
        */
		return json;
	}

	protected Response getJsonResponse(String uuid, UriInfo uriInfo) {
		T pojo = getService().getByUuid(uuid);
		String json = new BumJsonProducer().getJson(pojo, getExpandParameters(uriInfo)).toString();
		return getResponse(Status.OK, json);
	}

	protected String getJson(T pojo, UriInfo uriInfo) {
		return new BumJsonProducer().getJson(pojo, getExpandParameters(uriInfo)).toString();
	}

	protected List<String> getExpandParameters(UriInfo uriInfo) {
		return this.getParameters(uriInfo, QueryParameters.PARAMETER_EXPAND);
	}

	private List<String> getParameters(UriInfo uriInfo, String parameterToMatch) {
		if (uriInfo != null)
			for (Map.Entry<String, List<String>> me : uriInfo.getQueryParameters().entrySet())
				if (parameterToMatch.equalsIgnoreCase(me.getKey()))
					return me.getValue();

		return new ArrayList<>();
	}

	protected Response getBadJsonResponse(String passedJson) {
		return getErrorResponse(Status.BAD_REQUEST, "Server failed to parse json: <" + passedJson + ">.");
	}

	protected Response getResponse(Status status, String message) {
		return Response.status(status).entity(message).build();
	}

	protected Response getErrorResponse(Status status, String message) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode("-1");
		errorMessage.setMessage(message);

		return Response.status(status).entity(errorMessage.getJson()).build();
	}

	protected Response getErrorResponse(Status status, String code, String message) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode(code);
		errorMessage.setMessage(message);

		return Response.status(status).entity(errorMessage.getJson()).build();
	}

	protected abstract BumService<T> getService();

	protected abstract Class<T> getClazz();

}
