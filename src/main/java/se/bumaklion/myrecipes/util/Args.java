package main.java.se.bumaklion.myrecipes.util;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;

/**
 * @author olle
 */
public class Args {

	private Args() {
		// hidden
	}

	/**
	 * @param arg
	 *            the argument to check
	 * @param argName
	 *            the name of the argument
	 * @throws NullPointerException
	 *             if the argument is null
	 */
	public static <T> void notNull(T arg, String argName) {
		if (arg == null)
			throw new NullPointerException(argName + " may not be null!");
	}

	/**
	 * @param arg
	 *            the argument to check
	 * @param argName
	 *            the name of the argument
	 * @throws IllegalArgumentException
	 *             if the argument does not have a primary key
	 */
	public static <T extends BumPojo> void notTransient(T arg, String argName) {
		if (!BumPojoUtil.isSavedBefore(arg))
			throw new IllegalArgumentException(argName + " may not be transient!");
	}

	/**
	 * @param arg
	 *            the argument to check
	 * @param argName
	 *            the name of the argument
	 * @throws IllegalArgumentException
	 *             if the argument is empty (<code>null</code> or size 0)
	 */
	public static void notEmpty(String arg, String argName) {
		if (Strings.isEmpty(arg))
			throw new IllegalArgumentException(argName + " may not be empty!");
	}
}
