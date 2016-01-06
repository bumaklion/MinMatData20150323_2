package test.java.se.bumaklion.myrecipes.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.util.Args;
import test.java.se.bumaklion.myrecipes.BumTest;

/**
 * @author olle
 */
public class ArgsTest extends BumTest {

	@Test
	public void notNullTrue() {
		Exception thrownException = null;
		try {
			Args.notNull(null, "TestArgument");
		} catch (NullPointerException e) {
			thrownException = e;
		}

		assertNotNull(thrownException);
		assertEquals("TestArgument may not be null!", thrownException.getMessage());
	}

	@Test
	public void notNullFalse() {
		Args.notNull(new Object(), "TestArgument");
	}

	@Test
	public void notTransientTrue() {
		Exception thrownException = null;
		User u = new User();
		try {
			Args.notTransient(u, "TestArgument");
		} catch (IllegalArgumentException e) {
			thrownException = e;
		}

		assertNotNull(thrownException);
		assertEquals("TestArgument may not be transient!", thrownException.getMessage());
	}

	@Test
	public void notTransientFalse() {
		User u = new User();
		u.setUuid("letsPretendToBePersistent");
		Args.notTransient(u, "TestArgument");
	}

	@Test
	public void notEmptyTrue() {
		Exception thrownException = null;
		try {
			Args.notEmpty("", "TestArgument");
		} catch (IllegalArgumentException e) {
			thrownException = e;
		}

		assertNotNull(thrownException);
		assertEquals("TestArgument may not be empty!", thrownException.getMessage());
	}

	@Test
	public void notEmptyFalse() {
		Args.notEmpty("something", "TestArgument");
	}
}
