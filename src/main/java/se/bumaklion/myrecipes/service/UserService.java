package main.java.se.bumaklion.myrecipes.service;

import java.util.List;

import main.java.se.bumaklion.myrecipes.dao.UserDao;
import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.util.Args;

/**
 * @author olle
 */
public class UserService extends BumService<User> {

	/**
	 * @param user
	 *                the user to persist
	 * @return a persistent version of the user
	 */
	public User saveOrUpdate(User user) {
		Args.notNull(user, "user");
		Args.notEmpty(user.getLogin(), "user login");
		//TODO: om den ska sparas p� nytt m�ste login vara unikt.. kanske kasta exception annars?
		setUpdateInfo(user, User.class);
		return getDao().saveOrUpdate(user);
	}

	/**
	 * @param login
	 *                the login of the desired User
	 * @return the user with the passed login, or null of none can be found
	 */
	public User getUserByLogin(String login) {
		Args.notNull(login, "login");

		List<User> results = getDao().getUserByLogin(login);
		if (results.isEmpty())
			return null;
		return results.get(0);
	}

	protected UserDao getDao() {
		return new UserDao();
	}

	@Override
	protected Class<User> getClazz() {
		return User.class;
	}
}
