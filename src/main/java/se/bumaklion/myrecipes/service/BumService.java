package main.java.se.bumaklion.myrecipes.service;

import java.util.Date;

import main.java.se.bumaklion.myrecipes.dao.BumDao;
import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.util.BumPojoUtil;

/**
 * @author Olle
 */
public abstract class BumService<T extends BumPojo> {

	/**
	 * sets {@link BumPojo#getCreationDate()} (if not already set) and
	 * {@link BumPojo#getLastUpdate()}
	 * 
	 * @param pojo
	 *                the POJO to update
	 */
	public void setUpdateInfo(T pojo, Class<T> clazz) {
		Date now = new Date();
		if (!BumPojoUtil.isSavedBefore(pojo))
			pojo.setCreationDate(now);

		pojo.setLastUpdate(now);
	}

	/**
	 * sets {@link BumPojo#getCreationDate()} (if not already set) and
	 * {@link BumPojo#getLastUpdate()}
	 * 
	 * @param pojo
	 *                the POJO to update
	 */
	public void setUpdateInfo(T pojo) {
		setUpdateInfo(pojo, getClazz());
	}

	public T getByUuid(String uuid) {
		return getDao().getByUuid(uuid, getClazz());
	}

	protected abstract BumDao<T> getDao();

	protected abstract Class<T> getClazz();

}
