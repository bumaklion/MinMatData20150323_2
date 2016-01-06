package main.java.se.bumaklion.myrecipes.util;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;

/**
 * @author olle
 */
public class BumPojoUtil {

	/**
	 * @param p
	 *            the POJO to test
	 * @return <code>true</code> if and only if the passed POJO has a none empty
	 *         primary key field
	 */
	public static boolean isSavedBefore(BumPojo p) {
		if (p == null)
			return false;

		return !Strings.isEmpty(p.getUuid());
	}

}
