package main.java.se.bumaklion.myrecipes.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.UuidGenerator;
import main.java.se.bumaklion.myrecipes.util.Strings;
import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;


/**
 * @author Olle
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@UuidGenerator(name = "BUM_ID_GEN")
public class BumPojo {

	@Id
	@GeneratedValue(generator = "BUM_ID_GEN")
	@JsonField(scope = Scope.EXPORT)
	private String uuid;

	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonField(scope = Scope.EXPORT)
	private Date creationDate;

	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonField(scope = Scope.EXPORT)
	private Date lastUpdate;

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setCreationDate(Date arg0) {
		this.creationDate = arg0;
	}

	public void setLastUpdate(Date arg0) {
		this.lastUpdate = arg0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BumPojo other = (BumPojo) obj;

		if (Strings.isEmpty(uuid) && Strings.isEmpty(other.uuid))
			return this == other;

		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;

		return true;
	}

}
