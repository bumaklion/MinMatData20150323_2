package main.java.se.bumaklion.myrecipes.service;

import main.java.se.bumaklion.myrecipes.dao.RecipeIngredientDao;
import main.java.se.bumaklion.myrecipes.domain.RecipeIngredient;
import main.java.se.bumaklion.myrecipes.util.Args;

public class RecipeIngredientService extends BumService<RecipeIngredient> {

	public RecipeIngredient saveOrUpdate(RecipeIngredient ri) {
		Args.notNull(ri, "RecipeIngredient");
		Args.notTransient(ri.getMeasurement(), "measurement");
		Args.notTransient(ri.getIngredient(), "ingredient");
		Args.notTransient(ri.getRecipe(), "recipe");
		setUpdateInfo(ri);
		return getDao().saveOrUpdate(ri);
	}

	@Override
	protected RecipeIngredientDao getDao() {
		return new RecipeIngredientDao();
	}

	@Override
	protected Class<RecipeIngredient> getClazz() {
		return RecipeIngredient.class;
	}

}
