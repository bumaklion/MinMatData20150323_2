package main.java.se.bumaklion.myrecipes.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;

@Entity
@Table(name = "recipe")
public class Recipe extends BumPojo {

	@JsonField
	private String title;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	@JsonField(scope = Scope.EXPORT)
	private List<Instruction> instructions;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	@JsonField(scope = Scope.EXPORT)
	private List<RecipeIngredient> recipeIngredients;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	@JsonField(scope = Scope.EXPORT)
	private List<Comment> comments;

	@JsonField(scope = Scope.EXPORT)
	private User chef;

	// TODO pic

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}

	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public User getChef() {
		return chef;
	}

	public void setChef(User chef) {
		this.chef = chef;
	}

}
