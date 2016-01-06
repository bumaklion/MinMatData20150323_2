package main.java.se.bumaklion.myrecipes.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.util.annotations.JsonFieldAnnotationParser;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;
import main.java.se.bumaklion.myrecipes.util.json.help.JsonArray;
import main.java.se.bumaklion.myrecipes.util.json.help.JsonObject;
import main.java.se.bumaklion.myrecipes.util.json.help.JsonValue;

/**
 * @author Olle
 */
public class BumJsonProducer {

	private BumPojo mainPojo;
	private List<BumPojo> expandPojos;
	private List<String> expandParameters;
	private Scope scope = Scope.EXPORT;

	public JsonObject getJson(BumPojo pojo, Collection<String> expandables) {
		this.mainPojo = pojo;
		if (expandables == null)
			expandParameters = new ArrayList<>();
		else
			this.expandParameters = new ArrayList<>(expandables);
		// remove duplicates
		return getJson(pojo);
	}

	private JsonObject getJson(BumPojo pojo) {
		Map<String, Object> values = JsonFieldAnnotationParser.parse(pojo, scope);

		// build pojosToExpand if this is the 'first run'
		if (mainPojo.equals(pojo) && expandPojos == null) {
			expandPojos = new ArrayList<>();

			for (Map.Entry<String, Object> me : values.entrySet()) {
				if (me.getValue() instanceof BumPojo) {
					if (expandParameters.contains(me.getKey()))
						expandPojos.add((BumPojo) me.getValue());
				} else if (me.getValue() instanceof Iterable<?>) {
					for (Object obj : (Iterable<?>) me.getValue())
						if (obj instanceof BumPojo)
							if (expandParameters.contains(me.getKey()))
								expandPojos.add((BumPojo) obj);
				}
			}
		}

		JsonObject json = new JsonObject();
		for (Map.Entry<String, Object> me : values.entrySet())
			populateJson(json, me.getKey(), me.getValue());

		return json;
	}

	private JsonObject getLazyLoadedObject(String name, BumPojo pojo) {
		JsonObject json = new JsonObject();
		json.add("uuid", pojo.getUuid());
		json.add("lazyLoaded", true);
		return json;
	}

	private void populateJson(JsonObject json, String name, Object o) {
		if (o instanceof JsonNullValue)
			return;

		if (o instanceof Iterable) {
			JsonArray arr = new JsonArray();

			for (Object obj : (Iterable<?>) o)
				if (obj instanceof BumPojo)
					arr.add(jsonValue(name, obj));

			json.add(name, arr);
		} else {
			json.add(name, jsonValue(name, o));
		}
	}

	private JsonValue jsonValue(String name, Object o) {
		if (o instanceof BumPojo) {
			if (!expandPojos.contains(o))
				return getLazyLoadedObject(name, (BumPojo) o);

			return getJson((BumPojo) o);
		} else if (o instanceof String)
			return JsonValue.valueOf((String) o);
		else if (o instanceof Date)
			return JsonValue.valueOf(JsonFormats.dateFormat.format(o));
		else if (o instanceof Integer)
			return JsonValue.valueOf((int) o);
		else if (o instanceof Double)
			return JsonValue.valueOf((double) o);
		else if (o instanceof Boolean)
			return JsonValue.valueOf((Boolean) o);
		else if (o instanceof Float)
			return JsonValue.valueOf((Float) o);
		else if (o instanceof Long)
			return JsonValue.valueOf((Long) o);

		throw new IllegalArgumentException("JsonProducer does not support the class: " + o.getClass());
	}

	/**
	 * Sets the scope of the JSON handling.<br>
	 * Default value is {@link Scope#EXPORT}.<br>
	 * By altering the scope, the producer can producer fields that are
	 * normally not intended to be exported.
	 * 
	 * @param scope
	 *                the new Scope
	 * @return this, for chaining
	 */
	public BumJsonProducer scope(Scope scope) {
		this.scope = scope;
		return BumJsonProducer.this;
	}
}
