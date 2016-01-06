package main.java.se.bumaklion.myrecipes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import main.java.se.bumaklion.myrecipes.util.PasswordHash;
import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;

@Entity
@Table(name = "min_mat_user")
public class User extends BumPojo {

	@JsonField
	@Column(unique = true)
	private String login;

	/**
	 * contains iteration count, salt and hashed password, see
	 * {@link PasswordHash}
	 */
	private String passwordHash;

	@Transient
	@JsonField(scope = Scope.IMPORT)
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", passwordHash=" + passwordHash + "]";
	}

}
