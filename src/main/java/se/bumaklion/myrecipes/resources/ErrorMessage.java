package main.java.se.bumaklion.myrecipes.resources;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.json.BumJsonProducer;

/**
 * Represents the content of an error response that can be easily converted to
 * JSON.
 * 
 * @author Olle
 */
public class ErrorMessage extends BumPojo {

	@JsonField
	private String code;
	@JsonField
	private String message;

	public String getJson() {
		return new BumJsonProducer().getJson(ErrorMessage.this, null).toString();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
