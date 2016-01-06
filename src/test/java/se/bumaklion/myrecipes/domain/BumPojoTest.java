package test.java.se.bumaklion.myrecipes.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import test.java.se.bumaklion.myrecipes.BumTest;

/**
 * @author olle
 */
public class BumPojoTest extends BumTest {

	@Test
	public void equalsNotSameNoUuids() {
		TestPojo p1 = new TestPojo(null);
		TestPojo p2 = new TestPojo(null);

		assertNotEquals(p1, p2);
	}

	@Test
	public void equalsNotSameNoUuidMatch() {
		TestPojo p1 = new TestPojo("p1");
		TestPojo p2 = new TestPojo("p2");

		assertNotEquals(p1, p2);
	}

	@Test
	public void equalsNotSameUuidMatch() {
		TestPojo p1 = new TestPojo("p1");
		TestPojo p2 = new TestPojo("p1");

		assertEquals(p1, p2);
	}

	@Test
	public void equalsSamePojo() {
		TestPojo p1 = new TestPojo("p1");
		assertEquals(p1, p1);
	}

	@Test
	public void equalsSamePojoNullUuid() {
		TestPojo p1 = new TestPojo(null);
		assertEquals(p1, p1);
	}

	@Test
	public void sameUuidDifferentClasses() {
		TestPojo p1 = new TestPojo("olle");
		OtherTestPojo p2 = new OtherTestPojo("olle");

		assertNotEquals(p1, p2);
	}

	@Test
	public void nullUuidDifferentClasses() {
		TestPojo p1 = new TestPojo(null);
		OtherTestPojo p2 = new OtherTestPojo(null);

		assertNotEquals(p1, p2);
	}

	public class TestPojo extends BumPojo {
		public TestPojo(String uuid) {
			setUuid(uuid);
		}
	}

	public class OtherTestPojo extends BumPojo {
		public OtherTestPojo(String uuid) {
			setUuid(uuid);
		}
	}

}
