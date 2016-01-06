package main.java.se.bumaklion.myrecipes.service;

import java.util.List;

import main.java.se.bumaklion.myrecipes.dao.RecipeDao;
import main.java.se.bumaklion.myrecipes.domain.Recipe;
import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.util.Args;

public class RecipeService extends BumService<Recipe> {

	public Recipe saveOrUpdate(Recipe recipe) {
		Args.notNull(recipe, "recipe");
		Args.notNull(recipe.getChef(), "chef");
		Args.notTransient(recipe.getChef(), "chef");
		Args.notEmpty(recipe.getTitle(), "recipe title");

		setUpdateInfo(recipe, Recipe.class);
		return getDao().saveOrUpdate(recipe);
	}

	public List<Recipe> getRecipes(User user) {
		return getDao().getRecipes(user);
	}

	protected RecipeDao getDao() {
		return new RecipeDao();
	}

	@Override
	protected Class<Recipe> getClazz() {
		return Recipe.class;
	}

}
