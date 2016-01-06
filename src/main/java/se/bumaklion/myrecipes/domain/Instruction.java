package main.java.se.bumaklion.myrecipes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;

@Entity
@Table(name = "instruction")
public class Instruction extends BumPojo {

	@JsonField
	private int instructionIndex;

	@Column(columnDefinition = "TEXT")
	@JsonField
	private String instructionText;

	@JoinColumn(name = "RECIPE_UUID")
	@JsonField(scope = Scope.EXPORT)
	private Recipe recipe;

	public Recipe getRecipe() {
		return recipe;
	}

	public int getInstructionIndex() {
		return instructionIndex;
	}

	public void setInstructionIndex(int instructionIndex) {
		this.instructionIndex = instructionIndex;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public String getInstructionText() {
		return instructionText;
	}

	public void setInstructionText(String instructionText) {
		this.instructionText = instructionText;
	}

}
