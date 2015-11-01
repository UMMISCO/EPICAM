package org.imogene.epicam.server.birtPojo;

import org.imogene.epicam.domain.entity.DistrictSante;
import org.imogene.epicam.domain.entity.Region;

public class RegTB {
	
	String columnIdPatient;
	String columnNomPatient;
	String columnSexePatient;
	int columnAgePatient;
	String columnProfessionPatient;
	
	public RegTB() {
		super();
	}

	public RegTB(String columnIdPatient, String columnNomPatient,
			String columnSexePatient, int columnAgePatient,
			String columnProfessionPatient) {
		super();
		this.columnIdPatient = columnIdPatient;
		this.columnNomPatient = columnNomPatient;
		this.columnSexePatient = columnSexePatient;
		this.columnAgePatient = columnAgePatient;
		this.columnProfessionPatient = columnProfessionPatient;
	}

	public String getColumnIdPatient() {
		return columnIdPatient;
	}

	public void setColumnIdPatient(String columnIdPatient) {
		this.columnIdPatient = columnIdPatient;
	}

	public String getColumnNomPatient() {
		return columnNomPatient;
	}

	public void setColumnNomPatient(String columnNomPatient) {
		this.columnNomPatient = columnNomPatient;
	}

	public String getColumnSexePatient() {
		return columnSexePatient;
	}

	public void setColumnSexePatient(String columnSexePatient) {
		this.columnSexePatient = columnSexePatient;
	}

	public int getColumnAgePatient() {
		return columnAgePatient;
	}

	public void setColumnAgePatient(int columnAgePatient) {
		this.columnAgePatient = columnAgePatient;
	}

	public String getColumnProfessionPatient() {
		return columnProfessionPatient;
	}

	public void setColumnProfessionPatient(String columnProfessionPatient) {
		this.columnProfessionPatient = columnProfessionPatient;
	}


}
