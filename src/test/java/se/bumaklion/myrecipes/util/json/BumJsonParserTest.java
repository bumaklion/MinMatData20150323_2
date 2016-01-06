package test.java.se.bumaklion.myrecipes.util.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonProcessingException;
import org.junit.Test;

import main.java.se.bumaklion.myrecipes.util.DateUtil;
import main.java.se.bumaklion.myrecipes.util.Strings;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonParser;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonProducer;
import test.java.se.bumaklion.myrecipes.BumTest;

public class BumJsonParserTest extends BumTest {

	@Test
	public void mm() throws JsonProcessingException, IOException {
		//testa arv										X
		//testa alla javatyper								X
		//bumpojo?
		//collection?
		//om f�ltet inte finns i jsonm ska det inte uppdaters		X
		//om f�ltet finns men �r tomt ska vi t�mma pojons f�lt.		X
		//enbarty @External								X
		//skapa											X

		//n�r det �r m�nga till m�nga s� anv�nder man en  egen resource f�r det XxYyConnection.
		//hur g�r man annars? beh�ver vi bumpojo och collection h�r?
	}

	@Test
	public void importField() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setImportScopeField("myImport");
		String json = new BumJsonProducer().scope(Scope.BOTH).getJson(pojo, null).toString();

		TestPojo created = new BumJsonParser().create(TestPojo.class, json);
		assertEquals("myImport", created.getImportScopeField());
	}

	@Test
	public void bothScopeField() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setBothScopeField("myImport");
		String json = new BumJsonProducer().scope(Scope.BOTH).getJson(pojo, null).toString();

		TestPojo created = new BumJsonParser().create(TestPojo.class, json);
		assertEquals("myImport", created.getBothScopeField());
	}

	@Test
	public void exportField() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setExportScopeField("myExport");
		String json = new BumJsonProducer().scope(Scope.BOTH).getJson(pojo, null).toString();

		TestPojo created = new BumJsonParser().create(TestPojo.class, json);
		assertTrue(Strings.isEmpty(created.getExportScopeField()));
	}

	@Test
	public void create() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setStringField("myString");
		String json = new BumJsonProducer().getJson(pojo, null).toString();

		TestPojo created = new BumJsonParser().create(TestPojo.class, json);
		assertEquals("myString", created.getStringField());
	}

	@Test
	public void updateString() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setStringField("myString");

		String json = new BumJsonProducer().getJson(pojo, null).toString();
		pojo.setStringField("somethingElse");

		pojo = new BumJsonParser().update(pojo, json);

		assertEquals("myString", pojo.getStringField());
	}

	@Test
	public void updateInt() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setIntField(4);

		String json = new BumJsonProducer().getJson(pojo, null).toString();
		pojo.setIntField(8);

		pojo = new BumJsonParser().update(pojo, json);

		assertEquals(4, pojo.getIntField());
	}

	@Test
	public void updateInteger() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setIntegerField(4);

		String json = new BumJsonProducer().getJson(pojo, null).toString();
		pojo.setIntegerField(8);

		pojo = new BumJsonParser().update(pojo, json);

		assertEquals(new Integer(4), pojo.getIntegerField());
	}

	@Test
	public void updatePrimitiveDouble() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setPrimitiveDoubleField(4.1337);

		String json = new BumJsonProducer().getJson(pojo, null).toString();
		pojo.setPrimitiveDoubleField(8.26);

		pojo = new BumJsonParser().update(pojo, json);

		assertEquals(new Double(4.1337), new Double(pojo.getPrimitiveDoubleField()));
	}

	@Test
	public void updateDouble() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setDoubleField(4.1337);

		String json = new BumJsonProducer().getJson(pojo, null).toString();
		pojo.setDoubleField(8.26);

		pojo = new BumJsonParser().update(pojo, json);

		assertEquals(new Double(4.1337), pojo.getDoubleField());
	}

	@Test
	public void updateDate() throws Exception {
		TestPojo pojo = new TestPojo();
		Date jsonDate = DateUtil.getDate(2013, 01, 02);
		pojo.setDateField(jsonDate);

		String json = new BumJsonProducer().getJson(pojo, null).toString();
		pojo.setDateField(DateUtil.getDate(2012, 06, 06));

		pojo = new BumJsonParser().update(pojo, json);

		assertEquals(jsonDate, pojo.getDateField());
	}

	@Test
	public void updateInheritedFields() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setUuid("myUuid");

		String json = new BumJsonProducer().getJson(pojo, null).toString();
		pojo.setUuid("somethingElse");

		pojo = new BumJsonParser().scope(Scope.BOTH).update(pojo, json);

		assertEquals("myUuid", pojo.getUuid());
	}

	@Test
	public void updateDontNullNoneExistingJsonFields() throws Exception {
		TestPojo pojo = new TestPojo();
		String json = new BumJsonProducer().getJson(pojo, null).toString();
		pojo.setStringField("I shall persist!");

		pojo = new BumJsonParser().update(pojo, json);

		assertEquals("I shall persist!", pojo.getStringField());
	}

	@Test
	public void updateDoUpdateIfValueIsEmpty() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setStringField("");//not null!
		String json = new BumJsonProducer().getJson(pojo, null).toString();
		pojo.setStringField("I shall be overwritten!");

		pojo = new BumJsonParser().update(pojo, json);

		assertEquals("", pojo.getStringField());
	}

	@Test
	public void updateOnlyExternals() throws Exception {
		TestPojo pojo = new TestPojo();
		pojo.setStringField("str field");
		pojo.setNotExternal("something");
		String json = new BumJsonProducer().getJson(pojo, null).toString();

		pojo.setStringField("override me");
		pojo.setNotExternal("let me be");

		pojo = new BumJsonParser().update(pojo, json);

		assertEquals("str field", pojo.getStringField());
		assertEquals("let me be", pojo.getNotExternal());
	}

}
