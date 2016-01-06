package test.java.se.bumaklion.myrecipes.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import main.java.se.bumaklion.myrecipes.dao.BumDao;
import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.service.BumService;
import main.java.se.bumaklion.myrecipes.util.DateUtil;
import test.java.se.bumaklion.myrecipes.BumTest;

/**
 * @author olle
 */
public class BumServiceTest extends BumTest {

	private BumService<BumPojo> bumService = new BumService<BumPojo>() {

		@Override
		protected BumDao<BumPojo> getDao() {
			return new BumDao<BumPojo>();
		}

		@Override
		protected Class<BumPojo> getClazz() {
			return BumPojo.class;
		}
	};

	@Test
	public void setUpdateInfoPreviouslySaved() {
		BumPojo p = new BumPojo();
		p.setUuid("saved");
		Date creationDate = DateUtil.getDate(2012, 5, 3);

		p.setCreationDate(creationDate);
		p.setLastUpdate(DateUtil.getDate(2012, 8, 20));

		Date before = new Date();
		bumService.setUpdateInfo(p, BumPojo.class);

		// setUpdateInfo takes more than 20 millis to complete, this test will fail
		assertTrue(Math.abs((p.getLastUpdate().getTime() - before.getTime())) < 20);
		assertEquals(p.getCreationDate(), creationDate);
	}

	@Test
	public void setUpdateInfoPreviouslyNotSaved() {
		BumPojo p = new BumPojo();

		p.setCreationDate(DateUtil.getDate(2012, 5, 3));
		p.setLastUpdate(DateUtil.getDate(2012, 8, 20));

		Date before = new Date();
		bumService.setUpdateInfo(p, BumPojo.class);

		// setUpdateInfo takes more than 20 millis to complete, this test will  fail
		assertTrue(Math.abs((p.getLastUpdate().getTime() - before.getTime())) < 20);
		assertTrue(Math.abs((p.getCreationDate().getTime() - before.getTime())) < 20);
	}

}
