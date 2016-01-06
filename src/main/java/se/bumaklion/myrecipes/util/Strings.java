package main.java.se.bumaklion.myrecipes.util;

/**
 * @author olle
 */
public class Strings {

	/**
	 * @param s
	 *                the String to test
	 * @return true if and only if the following is true:<br>
	 *         1) s is null<br>
	 *         1) trim().isEmpty()
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	/**
	 * @param stringToTest
	 * @param possiblePart
	 * @return <code>true</code> if and only if the stringToTest contains
	 *         the possiblePart
	 */
	public static boolean containsIgnoreCase(String stringToTest, String possiblePart) {
		if (isEmpty(stringToTest) || isEmpty(possiblePart))
			return false;

		return stringToTest.toLowerCase().contains(possiblePart.toLowerCase());
	}

	/**
	 * This method is case sensitive.Example:<br>
	 * "kalle", "ll" returns "ka".
	 * 
	 * @param original
	 * @param stopString
	 * @return the original String minus the stopString and all characters
	 *         after the stopString. If the original String does not contain
	 *         the stopString, the original String is returned.
	 */
	public static String getEverythingBefore(String original, String stopString) {
		if (isEmpty(original))
			return original;

		if (isEmpty(stopString))
			return original;

		if (!original.contains(stopString))
			return original;

		return original.substring(0, original.indexOf(stopString));
	}

	/**
	 * This method is case sensitive.Example:<br>
	 * "kalle", "ll" returns "e".
	 * 
	 * 
	 * @param original
	 * @param divider
	 * @return the original String minus the stopString and all characters
	 *         before the divider. If the original String does not contain
	 *         the divider, the original String is returned.
	 */
	public static String getEverythingAfter(String original, String divider) {
		if (isEmpty(original))
			return original;

		if (isEmpty(divider))
			return original;

		if (!original.contains(divider))
			return original;

		return original.substring(original.indexOf(divider) + divider.length(), original.length());
	}

}
