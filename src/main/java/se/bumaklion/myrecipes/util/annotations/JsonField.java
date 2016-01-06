package main.java.se.bumaklion.myrecipes.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonField {

	public static final String defaultValue = "";

	String name() default defaultValue;

	/**
	 * This field indicates the scope of the JSON field.
	 * <p>
	 * {@link Scope#IMPORT} means that the field should not be exported, and
	 * {@link Scope#EXPORT} means that we should ignore incoming values for
	 * this field. {@link Scope#BOTH} indicates that the scope has no
	 * restrictions.
	 * 
	 * @return the scope of the field, {@link Scope#BOTH} by default
	 */
	Scope scope() default Scope.BOTH;

}
