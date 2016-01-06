package test.java.se.bumaklion.myrecipes.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.util.BumPojoUtil;
import test.java.se.bumaklion.myrecipes.BumTest;

/**
 * @author olle
 */
public class BumPojoUtilTest extends BumTest {

	@Test
	public void isSavedBeforeTestNullInput() {
		assertFalse(BumPojoUtil.isSavedBefore(null));
	}

	@Test
	public void isSavedBeforeEmptyUuid() {
		assertFalse(BumPojoUtil.isSavedBefore(new BumPojo()));
	}

	@Test
	public void isSavedBeforeWithUuid() {
		BumPojo pojo = new BumPojo();
		pojo.setUuid("123");
		assertTrue(BumPojoUtil.isSavedBefore(pojo));
	}

}
