package test.java.se.bumaklion.myrecipes.util.json;

import java.util.Date;

import main.java.se.bumaklion.myrecipes.domain.BumPojo;
import main.java.se.bumaklion.myrecipes.util.annotations.JsonField;
import main.java.se.bumaklion.myrecipes.util.annotations.Scope;

/**
 * @author olle
 */
public class TestPojo extends BumPojo {

	@JsonField(scope = Scope.IMPORT)
	private String importScopeField;

	@JsonField(scope = Scope.EXPORT)
	private String exportScopeField;

	@JsonField(scope = Scope.BOTH)
	private String bothScopeField;

	@JsonField
	private String stringField;

	private String notExternal;

	@JsonField
	private int intField;

	@JsonField
	private Integer integerField;

	@JsonField
	private double primitiveDoubleField;

	@JsonField
	private Double doubleField;

	@JsonField
	private Date dateField;

	public int getIntField() {
		return intField;
	}

	public void setIntField(int intField) {
		this.intField = intField;
	}

	public Integer getIntegerField() {
		return integerField;
	}

	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public double getPrimitiveDoubleField() {
		return primitiveDoubleField;
	}

	public void setPrimitiveDoubleField(double primitiveDoubleField) {
		this.primitiveDoubleField = primitiveDoubleField;
	}

	public Double getDoubleField() {
		return doubleField;
	}

	public void setDoubleField(Double doubleField) {
		this.doubleField = doubleField;
	}

	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

	public String getNotExternal() {
		return notExternal;
	}

	public void setNotExternal(String notExternal) {
		this.notExternal = notExternal;
	}

	public String getImportScopeField() {
		return importScopeField;
	}

	public void setImportScopeField(String importScopeField) {
		this.importScopeField = importScopeField;
	}

	public String getExportScopeField() {
		return exportScopeField;
	}

	public void setExportScopeField(String exportScopeField) {
		this.exportScopeField = exportScopeField;
	}

	public String getBothScopeField() {
		return bothScopeField;
	}

	public void setBothScopeField(String bothScopeField) {
		this.bothScopeField = bothScopeField;
	}

}
