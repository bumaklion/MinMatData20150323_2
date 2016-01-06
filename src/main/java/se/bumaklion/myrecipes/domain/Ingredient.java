package main.java.se.bumaklion.myrecipes.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;

/**
 * @author Olle
 */
@Entity
@Table(name = "ingridient")
public class Ingredient extends BumPojo {

	@JsonField
	private String name;

	@OneToMany(targetEntity = RecipeIngredient.class, mappedBy = "ingredient")
	@JsonField(scope = Scope.EXPORT)
	private List<RecipeIngredient> recipeIngredients;

	// ingridients withput user are 'global'
	private User user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

}
