package main.java.se.bumaklion.myrecipes.dao;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



/**
 * @author olle
 */
public class BumDao<T extends BumPojo> {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("MyRecipes");

	public T saveOrUpdate(T pojo) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		pojo = em.merge(pojo);
		em.getTransaction().commit();
		return pojo;
	}

	public T getByUuid(String uuid, Class<T> clazz) {
		EntityManager em = getEntityManager();
		return em.find(clazz, uuid);
	}

	protected synchronized EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}
