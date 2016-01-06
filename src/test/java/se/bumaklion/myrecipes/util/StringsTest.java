package test.java.se.bumaklion.myrecipes.util;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.se.bumaklion.myrecipes.util.Strings;
import test.java.se.bumaklion.myrecipes.BumTest;

public class StringsTest extends BumTest {

	@Test
	public void isEmptyNull() {
		assertTrue(Strings.isEmpty(null));
	}

	@Test
	public void isEmptyBlank() {
		assertTrue(Strings.isEmpty(""));
	}

	@Test
	public void isEmptySpaces() {
		assertTrue(Strings.isEmpty("  "));
	}

	@Test
	public void isEmptyNot() {
		assertFalse(Strings.isEmpty("h"));
	}

	@Test
	public void containsIgnoreCase() {
		assertTrue(Strings.containsIgnoreCase("kalle", "LL"));
	}

	@Test
	public void containsIgnoreCaseEmptyTestString() {
		assertFalse(Strings.containsIgnoreCase(null, "LL"));
		assertFalse(Strings.containsIgnoreCase("", "LL"));
	}

	@Test
	public void containsIgnoreCaseEmptyPartString() {
		assertFalse(Strings.containsIgnoreCase("kalle", null));
		assertFalse(Strings.containsIgnoreCase("kalle", ""));
	}

	@Test
	public void getEverythingBefore() {
		assertEquals("aba", Strings.getEverythingBefore("aba:ftw", ":"));
	}

	@Test
	public void getEverythingBeforeMultipleChars() {
		assertEquals("aba", Strings.getEverythingBefore("aba:ftw", ":f"));
	}

	@Test
	public void getEverythingBeforeFirstChars() {
		assertEquals("", Strings.getEverythingBefore("aba:ftw", "a"));
	}

	@Test
	public void getEverythingBeforeDoesNotContain() {
		assertEquals("aba:ftw", Strings.getEverythingBefore("aba:ftw", "wah?"));
	}

	@Test
	public void getEverythingAfter() {
		assertEquals("jonas", Strings.getEverythingAfter("kalle:jonas", ":"));
	}

	@Test
	public void getEverythingAfterMultipleChars() {
		assertEquals("jonas", Strings.getEverythingAfter("kalle:jonas", "e:"));
	}

	@Test
	public void getEverythingAfterLastChars() {
		assertEquals("", Strings.getEverythingAfter("kalle:jonas", "s"));
	}

	@Test
	public void getEverythingAfterLastCharsDoesNotContain() {
		assertEquals("kalle:jonas", Strings.getEverythingAfter("kalle:jonas", "�"));
	}
}
