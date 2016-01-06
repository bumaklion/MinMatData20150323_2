package test.java.se.bumaklion.myrecipes.service;

import org.junit.Test;
import org.mockito.Mockito;

import main.java.se.bumaklion.myrecipes.dao.RecipeDao;
import main.java.se.bumaklion.myrecipes.domain.Recipe;
import main.java.se.bumaklion.myrecipes.domain.User;
import main.java.se.bumaklion.myrecipes.service.RecipeService;
import test.java.se.bumaklion.myrecipes.BumTest;

public class RecipeServiceTest extends BumTest {

	private RecipeDao mockDao = Mockito.mock(RecipeDao.class);
	private RecipeService recipeService = new RecipeService() {

		protected RecipeDao getDao() {
			return mockDao;
		};
	};

	@Test(expected = NullPointerException.class)
	public void saveOrUpdateNullRecipe() {
		recipeService.saveOrUpdate(null);
		Mockito.verify(mockDao, Mockito.times(0)).saveOrUpdate(null);
		Mockito.verify(recipeService, Mockito.times(0)).setUpdateInfo(null, Recipe.class);
	}

	@Test(expected = NullPointerException.class)
	public void saveOrUpdateNoChef() {
		Recipe recipe = new Recipe();
		recipeService.saveOrUpdate(recipe);
		Mockito.verify(mockDao, Mockito.times(0)).saveOrUpdate(recipe);
		Mockito.verify(recipeService, Mockito.times(0)).setUpdateInfo(recipe, Recipe.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveOrUpdateChefNotSaved() {
		Recipe r = new Recipe();
		r.setChef(new User());
		r.setTitle("my title");
		recipeService.saveOrUpdate(r);
		Mockito.verify(mockDao, Mockito.times(0)).saveOrUpdate(r);
		Mockito.verify(recipeService, Mockito.times(0)).setUpdateInfo(r, Recipe.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveOrUpdateNoTitle() {
		Recipe r = new Recipe();
		User chef = new User();
		chef.setUuid("123");
		r.setChef(chef);
		recipeService.saveOrUpdate(r);
		Mockito.verify(mockDao, Mockito.times(0)).saveOrUpdate(r);
		Mockito.verify(recipeService, Mockito.times(0)).setUpdateInfo(r, Recipe.class);
	}

	@Test
	public void saveOrUpdate() {
		Recipe r = new Recipe();
		User chef = new User();
		chef.setUuid("123");
		r.setChef(chef);
		r.setTitle("my title");
		recipeService.saveOrUpdate(r);

		Mockito.verify(mockDao, Mockito.times(1)).saveOrUpdate(r);
	}

}
