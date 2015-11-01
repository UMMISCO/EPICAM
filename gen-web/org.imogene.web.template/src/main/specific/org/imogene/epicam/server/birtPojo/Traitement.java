package org.imogene.epicam.server.birtPojo;

public class Traitement {
	

	private String columnRegime;
	private String columnMedicaments;
	private String columnDateDebut;
	private String columnDateFin;
	
	public Traitement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Traitement(String columnRegime, String columnMedicaments,
			String columnDateDebut, String columnDateFin) {
		super();
		this.columnRegime = columnRegime;
		this.columnMedicaments = columnMedicaments;
		this.columnDateDebut = columnDateDebut;
		this.columnDateFin = columnDateFin;
	}

	public String getColumnRegime() {
		return columnRegime;
	}

	public void setColumnRegime(String columnRegime) {
		this.columnRegime = columnRegime;
	}

	public String getColumnMedicaments() {
		return columnMedicaments;
	}

	public void setColumnMedicaments(String columnMedicaments) {
		this.columnMedicaments = columnMedicaments;
	}

	public String getColumnDateDebut() {
		return columnDateDebut;
	}

	public void setColumnDateDebut(String columnDateDebut) {
		this.columnDateDebut = columnDateDebut;
	}

	public String getColumnDateFin() {
		return columnDateFin;
	}

	public void setColumnDateFin(String columnDateFin) {
		this.columnDateFin = columnDateFin;
	}
}
