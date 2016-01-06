package main.java.se.bumaklion.myrecipes.util.json;

/**
 * We need to add null values as well so that we can use
 * {@link JsonFieldAnnotationParser} for getting all the annotated fields, not
 * just the none null ones (and we can't add null in the returned result)
 * 
 * @author olle
 */
public class JsonNullValue {

}
