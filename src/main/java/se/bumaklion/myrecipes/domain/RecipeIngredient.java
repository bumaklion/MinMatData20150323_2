package main.java.se.bumaklion.myrecipes.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient extends BumPojo {

	@JsonField
	private Ingredient ingredient;
	@JsonField
	private Measurement measurement;
	@JsonField
	private double amount;

	@JoinColumn(name = "RECIPE_UUID")
	@JsonField(scope = Scope.EXPORT)
	private Recipe recipe;

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
