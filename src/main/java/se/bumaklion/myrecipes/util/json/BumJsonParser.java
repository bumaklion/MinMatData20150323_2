package main.java.se.bumaklion.myrecipes.util.json;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.util.annotations.JsonFieldAnnotationParser;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;

/**
 * This way, we don't need to worry about none @External fields being updated.
 * We also have better control of how the json is parsed.
 * 
 * @author olle
 * 
 */
public class BumJsonParser {

	private Scope scope = Scope.IMPORT;

	public <T extends BumPojo> T create(Class<T> clazz, String json) throws IOException, IntrospectionException, ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		return update(clazz.newInstance(), json);
	}

	public <T extends BumPojo> T update(T pojo, String json) throws JsonProcessingException, IOException, IntrospectionException, ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		JsonNode pojoNode = new ObjectMapper().readTree(json);

		Map<String, Object> externalsInPojo = JsonFieldAnnotationParser.parse(pojo, scope);
		for (Map.Entry<String, Object> me : externalsInPojo.entrySet()) {
			JsonNode fieldNode = pojoNode.get(me.getKey());
			if (fieldNode == null)
				continue;

			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(me.getKey(), pojo.getClass());

			Object value = getValue(fieldNode, propertyDescriptor.getPropertyType());
			propertyDescriptor.getWriteMethod().invoke(pojo, value);
		}

		return pojo;
	}

	private Object getValue(JsonNode node, Class<?> type) throws ParseException {
		if (BumPojo.class.isAssignableFrom(type))
			//TODO: ta bort detta n�r bitarna �r p� plats
			throw new NullPointerException("DET VAR EN BUMPOJO, VAD SKA JAG G�RA?");
		//vi ska inte g�ra n�got h�r.. det ska inte finnas n�gra bumpojos som �r annotatade med import / both!

		if (type == Integer.class)
			return node.getIntValue();
		if (type == Double.class)
			return node.getDoubleValue();
		if (type == String.class)
			return node.getTextValue();
		if (type == Date.class) {
			String dateString = node.getTextValue();
			return JsonFormats.dateFormat.parse(dateString);
		}
		if (node.isNumber()) {
			//primitives
			switch (node.getNumberType()) {
			case INT:
				return node.getIntValue();
			case DOUBLE:
				return node.getDoubleValue();
			default:
				break;
			}
		}

		throw new UnsupportedOperationException("Can't parse this type: " + type + " string value: [" + node.getTextValue() + "].");
	}

	/**
	 * Sets the scope of the JSON parsing.<br>
	 * Default value is {@link Scope#IMPORT}.<br>
	 * By altering the scope, the parser can read fields that are normally
	 * not intended to be imported.
	 * 
	 * @param scope
	 *                the new Scope
	 * @return this, for chaining
	 */
	public BumJsonParser scope(Scope scope) {
		this.scope = scope;
		return BumJsonParser.this;
	}
}
