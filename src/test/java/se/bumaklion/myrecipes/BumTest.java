package test.java.se.bumaklion.myrecipes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Olle
 */
public class BumTest {

	/**
	 * Useful when comparing serialised dates (that looses their millisecond
	 * precision)
	 * 
	 * @param date
	 *            this Date will not be altered
	 * @return a Date with the same time as the passed date, but without
	 *         millisecond precision
	 */
	protected Date removeMillis(Date date) {
		long initialMillis = date.getTime();
		long lessPreciseMillis = 1000 * (initialMillis / 1000);

		return new Date(lessPreciseMillis);
	}

	public static Object invoke(Object obj, String methodName, Object... params) {
		int paramCount = params.length;

		Method method;
		Object requiredObj = null;
		Object[] parameters = new Object[paramCount];
		Class<?>[] classArray = new Class<?>[paramCount];
		for (int i = 0; i < paramCount; i++) {
			parameters[i] = params[i];
			classArray[i] = params[i].getClass();
		}
		try {
			method = obj.getClass().getDeclaredMethod(methodName, classArray);
			method.setAccessible(true);
			requiredObj = method.invoke(obj, params);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return requiredObj;
	}

}
