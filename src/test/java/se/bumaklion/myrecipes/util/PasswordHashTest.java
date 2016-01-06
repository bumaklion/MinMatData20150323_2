package test.java.se.bumaklion.myrecipes.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;

import main.java.se.bumaklion.myrecipes.util.PasswordHash;
import test.java.se.bumaklion.myrecipes.BumTest;

/**
 * @author Olle
 */
public class PasswordHashTest extends BumTest {

	@Test
	public void uniquePasswords() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String hash1 = PasswordHash.createHash("myPass");
		String hash2 = PasswordHash.createHash("myPass");

		assertFalse(hash1.equals(hash2));
	}

	@Test
	public void correctPass() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String hash1 = PasswordHash.createHash("myPass");
		assertTrue(PasswordHash.validatePassword("myPass", hash1));
	}

	@Test
	public void wrongPass() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String hash1 = PasswordHash.createHash("myPass");
		assertFalse(PasswordHash.validatePassword("MYPASS", hash1));
	}

}
