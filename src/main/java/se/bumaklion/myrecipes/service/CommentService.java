package main.java.se.bumaklion.myrecipes.service;

import main.java.se.bumaklion.myrecipes.dao.BumDao;
import main.java.se.bumaklion.myrecipes.dao.CommentDao;
import main.java.se.bumaklion.myrecipes.domain.Comment;
import main.java.se.bumaklion.myrecipes.util.Args;

public class CommentService extends BumService<Comment> {

	public Comment saveOrUpdate(Comment comment) {
		Args.notNull(comment, "comment");
		Args.notTransient(comment.getUser(), "user");
		Args.notTransient(comment.getRecipe(), "recipe");
		Args.notEmpty(comment.getText(), "text");

		setUpdateInfo(comment);

		return getDao().saveOrUpdate(comment);
	}

	@Override
	protected BumDao<Comment> getDao() {
		return new CommentDao();
	}

	@Override
	protected Class<Comment> getClazz() {
		return Comment.class;
	}

}
