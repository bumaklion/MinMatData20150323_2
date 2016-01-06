package main.java.se.bumaklion.myrecipes.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;

@Entity
@Table(name = "measurement")
public class Measurement extends BumPojo {

	@JsonField
	private String name;

	// might need to add stuff here..

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
