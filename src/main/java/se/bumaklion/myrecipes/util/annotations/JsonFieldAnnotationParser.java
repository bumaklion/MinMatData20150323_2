package main.java.se.bumaklion.myrecipes.util.annotations;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.util.json.JsonNullValue;
import main.java.se.bumaklion.myrecipes.util.logging.BumLogger;

/**
 * @author olle
 */
public class JsonFieldAnnotationParser {

	/**
	 * 
	 * @param pojo
	 *                the {@link BumPojo} with the @Extrnal fields
	 * @return a map where the keys are the names of the External fields and
	 *         the values are the values of the corresponding fields.
	 */
	public static Map<String, Object> parse(BumPojo pojo, Scope scope) {
		List<Class<? extends BumPojo>> classes = new ArrayList<>();
		addAllAnnotatedClasses(classes, pojo.getClass());
		Collections.reverse(classes);

		Map<String, Object> externalValues = new LinkedHashMap<>();
		for (Class<? extends BumPojo> clazz : classes)
			externalValues.putAll(getExternalValues(clazz, pojo, scope));

		return externalValues;
	}

	private static Map<String, Object> getExternalValues(Class<? extends BumPojo> clazz, BumPojo pojo, Scope scope) {
		Map<String, Object> values = new LinkedHashMap<>();

		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			if (f.isAnnotationPresent(JsonField.class)) {
				JsonField jsonField = f.getAnnotation(JsonField.class);

				if (scope != Scope.BOTH && jsonField.scope() != scope && jsonField.scope() != Scope.BOTH)
					//the scope does not match
					continue;

				Object o = null;
				try {
					o = new PropertyDescriptor(f.getName(), pojo.getClass()).getReadMethod().invoke(pojo);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e1) {
					BumLogger.error(e1);
				}

				if (o == null)
					o = new JsonNullValue();

				String name = jsonField.name().trim();
				if (JsonField.defaultValue.equalsIgnoreCase(name))
					name = f.getName();

				values.put(name, o);
			}
		}

		return values;
	}

	@SuppressWarnings("unchecked")
	private static void addAllAnnotatedClasses(List<Class<? extends BumPojo>> classes, Class<? extends BumPojo> classToAdd) {
		classes.add(classToAdd);

		Class<?> superClass = classToAdd.getSuperclass();
		if (superClass != null && BumPojo.class.isAssignableFrom(superClass))
			addAllAnnotatedClasses(classes, (Class<? extends BumPojo>) superClass);
	}

}
