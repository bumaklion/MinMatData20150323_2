package main.java.se.bumaklion.myrecipes.service;

import main.java.se.bumaklion.myrecipes.dao.BumDao;
import main.java.se.bumaklion.myrecipes.dao.InstructionDao;
import main.java.se.bumaklion.myrecipes.domain.Instruction;
import main.java.se.bumaklion.myrecipes.util.Args;

public class InstructionService extends BumService<Instruction> {

	public Instruction saveOrUpdate(Instruction instruction) {
		Args.notNull(instruction, "instruction");
		Args.notNull(instruction.getInstructionText(), "text");
		Args.notTransient(instruction.getRecipe(), "recipe");

		setUpdateInfo(instruction);

		return getDao().saveOrUpdate(instruction);
	}

	@Override
	protected BumDao<Instruction> getDao() {
		return new InstructionDao();
	}

	@Override
	protected Class<Instruction> getClazz() {
		return Instruction.class;
	}

}
