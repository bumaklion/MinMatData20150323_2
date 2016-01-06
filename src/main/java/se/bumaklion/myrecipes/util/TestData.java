package main.java.se.bumaklion.myrecipes.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.se.bumaklion.myrecipes.domain.Instruction;
import main.java.se.bumaklion.myrecipes.domain.Recipe;

public class TestData {

	public static List<Recipe> getRecipies() {
		List<Recipe> recipes = new ArrayList<>();

		recipes.add(getRecipe("Potatismos", "tvätta potatis", "koka potatis", "mosa potatis"));
		recipes.add(getRecipe("Nudlar", "koka vatten", "lägg i nudlar", "krydda"));
		recipes.add(getRecipe("Bacon", "Öppna paketet", "stek till gott"));

		return recipes;
	}

	private static Instruction getInstruction(String text) {
		Instruction i = new Instruction();
		i.setInstructionText(text);
		// i.setUuid(new Random().nextDouble() + "");
		return i;
	}

	private static Recipe getRecipe(String title, String... instructions) {
		Recipe r = new Recipe();
		r.setCreationDate(new Date());
		r.setLastUpdate(new Date(new Date().getTime() + 36000));
		r.setTitle(title);

		ArrayList<Instruction> instructionsList = new ArrayList<Instruction>();
		for (String s : instructions) {
			Instruction i = getInstruction(s);
			instructionsList.add(i);
			int index = instructionsList.indexOf(i);
			i.setInstructionIndex(index);
			i.setRecipe(r);
		}

		r.setInstructions(instructionsList);

		return r;
	}

}
