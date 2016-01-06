package main.java.se.bumaklion.myrecipes.dao;

import java.util.List;

import main.java.se.bumaklion.myrecipes.domain.Recipe;
import main.java.se.bumaklion.myrecipes.domain.User;

/**
 * @author olle
 */
public class RecipeDao extends BumDao<Recipe> {

	public List<Recipe> getRecipes(User user) {
		return getEntityManager().createQuery("SELECT res FROM Recipe res where res.chef = ?1", Recipe.class).setParameter(1, user).getResultList();
	}

}
