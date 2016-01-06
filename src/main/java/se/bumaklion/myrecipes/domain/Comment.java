package main.java.se.bumaklion.myrecipes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;

/**
 * @author Olle
 */
@Entity
@Table(name = "comment")
public class Comment extends BumPojo {

	@Column(columnDefinition = "TEXT")
	@JsonField
	private String text;

	@ManyToOne
	@JoinColumn(name = "RECIPE_UUID")
	@JsonField(scope = Scope.EXPORT)
	private Recipe recipe;

	@JsonField
	private User user;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
