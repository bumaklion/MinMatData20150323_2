package main.java.se.bumaklion.myrecipes.service;

import java.util.List;

import main.java.se.bumaklion.myrecipes.dao.IngredientDao;
import main.java.se.bumaklion.myrecipes.domain.Ingredient;
import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.util.Args;

/**
 * @author Olle
 */
public class IngredientService extends BumService<Ingredient> {

	public Ingredient saveOrUpdate(Ingredient ingridient) {
		Args.notNull(ingridient, "ingridient");
		Args.notEmpty(ingridient.getName(), "name");
		setUpdateInfo(ingridient);
		return getDao().saveOrUpdate(ingridient);
	}

	public List<Ingredient> getIngridients(User user) {
		return getDao().getIngridients(user);
	}

	@Override
	protected IngredientDao getDao() {
		return new IngredientDao();
	}

	@Override
	protected Class<Ingredient> getClazz() {
		return Ingredient.class;
	}

}
