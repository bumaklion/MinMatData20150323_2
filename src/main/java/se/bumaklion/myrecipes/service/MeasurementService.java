package main.java.se.bumaklion.myrecipes.service;

import java.util.List;

import main.java.se.bumaklion.myrecipes.dao.BumDao;
import main.java.se.bumaklion.myrecipes.dao.MeasurementDao;
import main.java.se.bumaklion.myrecipes.domain.Measurement;
import main.java.se.bumaklion.myrecipes.util.Args;

public class MeasurementService extends BumService<Measurement> {

	public Measurement saveOrUpdate(Measurement measurement) {
		Args.notNull(measurement, "measurement");
		Args.notEmpty(measurement.getName(), "name");

		setUpdateInfo(measurement);
		return getDao().saveOrUpdate(measurement);
	}

	public List<Measurement> getAll() {
		return new MeasurementDao().getAll();
	}

	@Override
	protected BumDao<Measurement> getDao() {
		return new MeasurementDao();
	}

	@Override
	protected Class<Measurement> getClazz() {
		return Measurement.class;
	}

}
