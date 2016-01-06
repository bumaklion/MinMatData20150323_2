package main.java.se.bumaklion.myrecipes.
dao;

import java.util.List;

import main.java.se.bumaklion.myrecipes.domain.Measurement;

public class MeasurementDao extends BumDao<Measurement> {

	public List<Measurement> getAll() {
		return getEntityManager().createQuery("SELECT m FROM Measurement m", Measurement.class).getResultList();
	}

}
