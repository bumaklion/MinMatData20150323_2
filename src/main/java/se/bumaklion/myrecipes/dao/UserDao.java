package main.java.se.bumaklion.myrecipes.dao;

import java.util.List;

import main.java.se.bumaklion.myrecipes.domain.User;

public class UserDao extends BumDao<User> {

	public List<User> getUserByLogin(String login) {
		return getEntityManager().createQuery("SELECT usr FROM User usr where usr.login = ?1", User.class).setParameter(1, login).getResultList();
	}

}
