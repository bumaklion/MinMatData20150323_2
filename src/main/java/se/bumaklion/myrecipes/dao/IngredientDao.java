package main.java.se.bumaklion.myrecipes.dao;

import java.util.List;

import main.java.se.bumaklion.myrecipes.domain.Ingredient;
import main.java.se.bumaklion.myrecipes.domain.User;

public class IngredientDao extends BumDao<Ingredient> {

	public List<Ingredient> getIngridients(User user) {
		return getEntityManager().createQuery("SELECT i FROM Ingridient i where i.user = ?1", Ingredient.class).setParameter(1, user).getResultList();
	}

}
