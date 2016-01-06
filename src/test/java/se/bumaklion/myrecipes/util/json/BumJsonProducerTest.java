package test.java.se.bumaklion.myrecipes.util.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonParser;
import org.junit.Ignore;
import org.junit.Test;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.domain.Measurement;
import main.java.se.bumaklion.myrecipes.service.MeasurementService;
import main.java.se.bumaklion.myrecipes.util.Strings;
import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonProducer;
import test.java.se.bumaklion.myrecipes.BumTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BumJsonProducerTest extends BumTest {

	private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Test
	public void dateTest() {
		Date d = new Date();
		TestPojo p = new TestPojo().date(d);

		assertEquals(removeMillis(d), serializeAndRecreate(p).date);
	}

	@Test
	@Ignore
	public void mm() {
		//fulkod f�r att skapa measurements

		MeasurementService service = new MeasurementService();

		Measurement m = new Measurement();
		m.setName("liter");
		m = service.saveOrUpdate(m);

		m = new Measurement();
		m.setName("deciliter");
		m = service.saveOrUpdate(m);

		m = new Measurement();
		m.setName("centiliter");
		m = service.saveOrUpdate(m);

		m = new Measurement();
		m.setName("milliliter");
		m = service.saveOrUpdate(m);

		m = new Measurement();
		m.setName("gram");
		m = service.saveOrUpdate(m);

		m = new Measurement();
		m.setName("kilo");
		m = service.saveOrUpdate(m);
	}

	@Test
	public void intTest() {
		TestPojo p = new TestPojo();
		p = serializeAndRecreate(p);
		assertEquals(0, p.getMyInt());
	}

	@Test
	public void intWithValueTest() {
		TestPojo p = new TestPojo();
		p.setMyInt(123);
		p = serializeAndRecreate(p);
		assertEquals(123, p.getMyInt());
	}

	@Test
	public void primitiveDoubleTest() {
		TestPojo p = new TestPojo();
		p = serializeAndRecreate(p);
		assertEquals(new Double(0.0), new Double(p.myPrimitiveDouble));
	}

	@Test
	public void primitiveDoubleWithValueTest() {
		TestPojo p = new TestPojo();
		p.setMyPrimitiveDouble(20.5);
		p = serializeAndRecreate(p);
		assertEquals(new Double(20.5), new Double(p.myPrimitiveDouble));
	}

	@Test
	public void doubleTest() {
		TestPojo p = new TestPojo();
		p.setMyDouble(22.55);
		p = serializeAndRecreate(p);
		assertEquals(new Double(22.55), p.getMyDouble());
	}

	@Test
	public void doubleWithNoValue() {
		TestPojo p = new TestPojo();
		p = serializeAndRecreate(p);
		assertEquals(null, p.getMyDouble());
	}

	@Test
	public void primitiveBoolean() {
		TestPojo p = new TestPojo();
		p.setMyPrimtiveBoolean(true);
		p = serializeAndRecreate(p);
		assertTrue(p.isMyPrimtiveBoolean());
	}

	@Test
	public void booleanTrue() {
		TestPojo p = new TestPojo();
		p.setMyBoolean(true);
		p = serializeAndRecreate(p);
		assertTrue(p.getMyBoolean());
	}

	@Test
	public void booleanNull() {
		TestPojo p = new TestPojo();
		p = serializeAndRecreate(p);
		assertNull(p.getMyBoolean());
	}

	@Test
	public void primitiveFloat() {
		TestPojo p = new TestPojo();
		p.setMyPrimitiveFloat(12123);
		p = serializeAndRecreate(p);
		assertEquals(new Float(12123), new Float(p.getMyPrimitiveFloat()));
	}

	@Test
	public void primitiveFloatNoValue() {
		TestPojo p = new TestPojo();
		p = serializeAndRecreate(p);
		assertEquals(new Float(0), new Float(p.getMyPrimitiveFloat()));
	}

	@Test
	public void floatTest() {
		TestPojo p = new TestPojo();
		p.setMyFloat(2255f);
		p = serializeAndRecreate(p);
		assertEquals(new Float(2255), p.getMyFloat());
	}

	@Test
	public void floatNullValue() {
		TestPojo p = new TestPojo();
		p = serializeAndRecreate(p);
		assertEquals(null, p.getMyFloat());
	}

	@Test
	public void primitiveLong() {
		TestPojo p = new TestPojo();
		p.setMyPrimitiveLong(12123);
		p = serializeAndRecreate(p);
		assertEquals(new Long(12123), new Long(p.getMyPrimitiveLong()));
	}

	@Test
	public void primitiveLongNoValue() {
		TestPojo p = new TestPojo();
		p = serializeAndRecreate(p);
		assertEquals(new Long(0), new Long(p.getMyPrimitiveLong()));
	}

	@Test
	public void longTest() {
		TestPojo p = new TestPojo();
		p.setMyLong(2255L);
		p = serializeAndRecreate(p);
		assertEquals(new Long(2255), p.getMyLong());
	}

	@Test
	public void longNullValue() {
		TestPojo p = new TestPojo();
		p = serializeAndRecreate(p);
		assertEquals(null, p.getMyLong());
	}

	@Test
	public void superClassTest() {
		TestPojo p = new TestPojo();
		p.setUuid("uuid in superclass");

		assertEquals("uuid in superclass", serializeAndRecreate(p).getUuid());
	}

	@Test
	public void notAnnotatedFieldTest() {
		TestPojo p = new TestPojo();
		p.setNotAnnotatedName("something");

		assertEquals(null, serializeAndRecreate(p).getNotAnnotatedName());
	}

	@Test
	public void jsonOrderTest() {
		// this is to make sure that the generated JSON fields are in the
		// same order as in the POJO, not in alphabetical order or something
		// else..

		Date d = new Date();
		String dateString = new SimpleDateFormat(DATE_FORMAT).format(d);

		TestPojo p = new TestPojo().name("my name").date(d);
		String json = getJson(p);

		assertTrue(json.indexOf("my name") < json.indexOf(dateString));
	}

	@Test
	public void jsonExpandablesDontExpand() {
		TestPojo child = new TestPojo().name("childPojo");
		child.setUuid("childUuid");

		TestPojo main = new TestPojo().name("mainPojo").child(child);

		TestPojo recreated = serializeAndRecreate(main);

		assertNotNull(recreated.getChild());
		assertEquals("childUuid", recreated.getChild().getUuid());
		assertTrue(Strings.isEmpty(recreated.getChild().getName()));
	}

	@Test
	public void jsonExpandablesDontExpandNullParameters() {
		TestPojo child = new TestPojo().name("childPojo");
		child.setUuid("childUuid");

		TestPojo main = new TestPojo().name("mainPojo").child(child);

		TestPojo recreated = serializeAndRecreate(main, null);

		assertNotNull(recreated.getChild());
		assertEquals("childUuid", recreated.getChild().getUuid());
		assertTrue(Strings.isEmpty(recreated.getChild().getName()));
	}

	@Test
	public void jsonExpandablesDoExpand() {
		TestPojo child = new TestPojo().name("childPojo");
		child.setUuid("childUuid");

		TestPojo main = new TestPojo().name("mainPojo").child(child);

		TestPojo recreated = serializeAndRecreate(main, getList("child"));

		assertNotNull(recreated.getChild());
		assertEquals("childPojo", recreated.getChild().getName());
	}

	@Test
	public void jsonExpandablesDoExpandLazyLoadedFlag() {
		LazyTestPojo child = new LazyTestPojo();
		child.setName("childUuid");
		child.setUuid("childUuid");

		LazyTestPojo main = new LazyTestPojo();
		main.setName("mainPojo");
		main.setLazyInitChild(child);

		List<String> empty = new ArrayList<>();
		String json = new BumJsonProducer().getJson(main, empty).toString();

		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
		LazyTestPojo lazyLoaded = gson.fromJson(json, LazyTestPojo.class);

		assertEquals("mainPojo", lazyLoaded.getName());
		assertFalse(lazyLoaded.isLazyLoaded());
		assertTrue(lazyLoaded.getLazyInitChild().isLazyLoaded());
	}

	@Test
	public void jsonExpandablesOnlyOneNestingLevel() {
		TestPojo grandChild = new TestPojo().name("grandChild");
		grandChild.setUuid("grandChildUuid");

		TestPojo child = new TestPojo().name("childPojo").child(grandChild);
		child.setUuid("childUuid");

		TestPojo main = new TestPojo().name("mainPojo").child(child);

		TestPojo recreated = serializeAndRecreate(main, getList("child"));

		assertNotNull(recreated.getChild().getChild());
		assertEquals("childPojo", recreated.getChild().getName());
		assertEquals("grandChildUuid", recreated.getChild().getChild().getUuid());
		assertTrue(Strings.isEmpty(recreated.getChild().getChild().getName()));
	}

	@Test
	public void jsonExpandablesOnlyOneNestingLevelSameExpandParameterTwice() {
		TestPojo grandChild = new TestPojo().name("grandChild");
		grandChild.setUuid("grandChildUuid");

		TestPojo child = new TestPojo().name("childPojo").child(grandChild);
		child.setUuid("childUuid");

		TestPojo main = new TestPojo().name("mainPojo").child(child);

		TestPojo recreated = serializeAndRecreate(main, getList("child", "child"));

		assertNotNull(recreated.getChild().getChild());
		assertEquals("childPojo", recreated.getChild().getName());
		assertEquals("grandChildUuid", recreated.getChild().getChild().getUuid());
		assertTrue(Strings.isEmpty(recreated.getChild().getChild().getName()));
	}

	@Test
	public void jsonExpandablesInList() {
		LazyTestPojo friend1 = new LazyTestPojo();
		friend1.setName("friend 1");

		LazyTestPojo friend2 = new LazyTestPojo();
		friend2.setName("friend 2");

		LazyTestPojo friend3 = new LazyTestPojo();
		friend3.setName("friend 3");

		List<LazyTestPojo> friends = new ArrayList<>();
		friends.add(friend1);
		friends.add(friend2);
		friends.add(friend3);

		TestPojo mainPojo = new TestPojo().name("main name");
		mainPojo.setFriends(friends);

		TestPojo recreated = serializeAndRecreate(mainPojo, getList("friends"));

		for (LazyTestPojo friend : recreated.getFriends())
			assertFalse(friend.lazyLoaded);
	}

	@Test
	public void jsonExpandablesInListOnlyOneNestingLevel() {
		TestPojo grandChild = new TestPojo().name("grand child");
		List<TestPojo> grandChildren = new ArrayList<>();
		grandChildren.add(grandChild);

		TestPojo child = new TestPojo().name("child");
		child.setPojos(grandChildren);
		TestPojo child2 = new TestPojo().name("child2");

		List<TestPojo> children = new ArrayList<>();
		children.add(child);
		children.add(child2);

		TestPojo mainPojo = new TestPojo().name("main name");
		mainPojo.setPojos(children);

		TestPojo recreated = serializeAndRecreate(mainPojo, getList("pojos"));

		for (TestPojo pojo : recreated.getPojos()) {
			assertFalse(pojo.isLazyLoaded());
			if (pojo.getPojos() != null)
				for (TestPojo p : pojo.getPojos())
					assertTrue(p.isLazyLoaded());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void UnsupportedFieldTest() {
		UnsupportedFieldHolder pojo = new UnsupportedFieldHolder();
		pojo.setUnsupported(new UnsupportedClass());
		serializeAndRecreate(pojo);
	}

	@Test
	public void scopeImport() {
		TestPojo p = new TestPojo();
		p.setImportScopeString("import");
		p = serializeAndRecreate(p);
		assertTrue(Strings.isEmpty(p.getImportScopeString()));
	}

	@Test
	public void scopeExport() {
		TestPojo p = new TestPojo();
		p.setExportScopeString("export");
		p = serializeAndRecreate(p);

		assertEquals("export", p.getExportScopeString());
	}

	@Test
	public void scopeBoth() {
		TestPojo p = new TestPojo();
		p.setBothScopeString("both");
		p = serializeAndRecreate(p);

		assertEquals("both", p.getBothScopeString());
	}

	private List<String> getList(String... expandables) {
		List<String> list = new ArrayList<>();
		for (String s : expandables)
			list.add(s);
		return list;
	}

	private TestPojo serializeAndRecreate(TestPojo pojo, List<String> expandables) {
		return getPojo(getJson(pojo, expandables));
	}

	private TestPojo serializeAndRecreate(BumPojo pojo) {
		return getPojo(getJson(pojo));
	}

	private TestPojo getPojo(String json) {
		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
		return gson.fromJson(json, TestPojo.class);
	}

	private String getJson(BumPojo pojo) {
		LinkedList<String> mm = new LinkedList<>();
		return getJson(pojo, mm);
	}

	private String getJson(BumPojo pojo, List<String> expandables) {
		return new BumJsonProducer().getJson(pojo, expandables).toString();
	}

	public class LazyTestPojo extends TestPojo {

		private boolean lazyLoaded;

		public LazyTestPojo() {
			// just so that hashcode and equals works which jsonProducer needs
			setUuid(new Random().nextLong() + "");
		}

		@JsonField
		private LazyTestPojo lazyInitChild;

		public boolean isLazyLoaded() {
			return lazyLoaded;
		}

		public void setLazyLoaded(boolean lazyLoaded) {
			this.lazyLoaded = lazyLoaded;
		}

		public LazyTestPojo getLazyInitChild() {
			return lazyInitChild;
		}

		public void setLazyInitChild(LazyTestPojo lazyInitChild) {
			this.lazyInitChild = lazyInitChild;
		}

	}

	public class TestPojo extends BumPojo {

		private boolean lazyLoaded;

		public TestPojo() {
			// just so that hashcode and equals works which jsonProducer needs
			setUuid(new Random().nextLong() + "");
		}

		@JsonField(scope = Scope.IMPORT)
		private String importScopeString;

		@JsonField(scope = Scope.EXPORT)
		private String exportScopeString;

		@JsonField(scope = Scope.BOTH)
		private String bothScopeString;

		@JsonField
		private Float myFloat;

		@JsonField
		private float myPrimitiveFloat;

		@JsonField
		private Long myLong;

		@JsonField
		private long myPrimitiveLong;

		@JsonField
		private boolean myPrimtiveBoolean;

		@JsonField
		private Boolean myBoolean;

		@JsonField
		private int myInt;

		@JsonField
		private double myPrimitiveDouble;

		@JsonField
		private Double myDouble;

		@JsonField
		private String name;

		private String notAnnotatedName;

		@JsonField
		private List<String> strings;

		@JsonField
		private List<TestPojo> pojos;

		@JsonField
		private TestPojo child;

		@JsonField
		private Date date;

		@JsonField
		private List<LazyTestPojo> friends;

		public TestPojo name(String name) {
			this.name = name;
			return this;
		}

		public TestPojo strings(String... strings) {
			this.strings = new ArrayList<>();
			for (String s : strings)
				this.strings.add(s);
			return this;
		}

		public TestPojo pojos(TestPojo... pojos) {
			this.pojos = new ArrayList<>();
			for (TestPojo pojo : pojos)
				this.pojos.add(pojo);
			return this;
		}

		public TestPojo date(Date date) {
			this.date = date;
			return this;
		}

		public TestPojo child(TestPojo child) {
			this.child = child;
			return this;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public TestPojo getChild() {
			return child;
		}

		public void setChild(TestPojo child) {
			this.child = child;
		}

		public List<String> getStrings() {
			return strings;
		}

		public void setStrings(List<String> strings) {
			this.strings = strings;
		}

		public List<TestPojo> getPojos() {
			return pojos;
		}

		public void setPojos(List<TestPojo> parents) {
			this.pojos = parents;
		}

		public String getNotAnnotatedName() {
			return notAnnotatedName;
		}

		public void setNotAnnotatedName(String notAnnotatedName) {
			this.notAnnotatedName = notAnnotatedName;
		}

		public List<LazyTestPojo> getFriends() {
			return friends;
		}

		public void setFriends(List<LazyTestPojo> friends) {
			this.friends = friends;
		}

		public boolean isLazyLoaded() {
			return lazyLoaded;
		}

		public void setLazyLoaded(boolean lazyLoaded) {
			this.lazyLoaded = lazyLoaded;
		}

		public int getMyInt() {
			return myInt;
		}

		public void setMyInt(int myInt) {
			this.myInt = myInt;
		}

		public double getMyPrimitiveDouble() {
			return myPrimitiveDouble;
		}

		public void setMyPrimitiveDouble(double myDouble) {
			this.myPrimitiveDouble = myDouble;
		}

		public Double getMyDouble() {
			return myDouble;
		}

		public void setMyDouble(Double myDouble) {
			this.myDouble = myDouble;
		}

		public boolean isMyPrimtiveBoolean() {
			return myPrimtiveBoolean;
		}

		public void setMyPrimtiveBoolean(boolean myPrimtiveBoolean) {
			this.myPrimtiveBoolean = myPrimtiveBoolean;
		}

		public Boolean getMyBoolean() {
			return myBoolean;
		}

		public void setMyBoolean(Boolean myBoolean) {
			this.myBoolean = myBoolean;
		}

		public Float getMyFloat() {
			return myFloat;
		}

		public void setMyFloat(Float myFloat) {
			this.myFloat = myFloat;
		}

		public float getMyPrimitiveFloat() {
			return myPrimitiveFloat;
		}

		public void setMyPrimitiveFloat(float myPrimitiveFloat) {
			this.myPrimitiveFloat = myPrimitiveFloat;
		}

		public Long getMyLong() {
			return myLong;
		}

		public void setMyLong(Long myLong) {
			this.myLong = myLong;
		}

		public long getMyPrimitiveLong() {
			return myPrimitiveLong;
		}

		public void setMyPrimitiveLong(long myPrimitiveLong) {
			this.myPrimitiveLong = myPrimitiveLong;
		}

		public String getImportScopeString() {
			return importScopeString;
		}

		public void setImportScopeString(String importScopeString) {
			this.importScopeString = importScopeString;
		}

		public String getExportScopeString() {
			return exportScopeString;
		}

		public void setExportScopeString(String exportScopeString) {
			this.exportScopeString = exportScopeString;
		}

		public String getBothScopeString() {
			return bothScopeString;
		}

		public void setBothScopeString(String bothScopeString) {
			this.bothScopeString = bothScopeString;
		}

	}

	public class UnsupportedClass {

	}

	public class UnsupportedFieldHolder extends BumPojo {
		@JsonField
		private UnsupportedClass unsupported;

		public UnsupportedClass getUnsupported() {
			return unsupported;
		}

		public void setUnsupported(UnsupportedClass unsupported) {
			this.unsupported = unsupported;
		}

	}
}
