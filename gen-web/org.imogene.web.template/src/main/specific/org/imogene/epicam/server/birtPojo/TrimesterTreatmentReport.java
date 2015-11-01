package org.imogene.epicam.server.birtPojo;

import java.util.Date;

public class TrimesterTreatmentReport {
	

	private int columnMonth;
	
	private String columnID;
	
	private Date columnDateEnregistrement;
	private String columnRegion;
	private String columnDistrict;
	private String columnCDT;

	private String columnDateDebut;
	private String columnDateFin;
	private String columnIssuTraitement;
	
	private Date columnDateEnr;
	private String columnTypePatient;
	
	public TrimesterTreatmentReport() {
		super();
	}

	public TrimesterTreatmentReport(Date columnDateEnregistrement,
			String columnRegion, String columnDistrict, String columnCDT,
			String columnDateDebut, String columnDateFin,
			String columnIssuTraitement) {
		super();
		this.columnDateEnregistrement = columnDateEnregistrement;
		this.columnRegion = columnRegion;
		this.columnDistrict = columnDistrict;
		this.columnCDT = columnCDT;
		this.columnDateDebut = columnDateDebut;
		this.columnDateFin = columnDateFin;
		this.columnIssuTraitement = columnIssuTraitement;
	}

	public Date getColumnDateEnregistrement() {
		return columnDateEnregistrement;
	}

	public void setColumnDateEnregistrement(Date columnDateEnregistrement) {
		this.columnDateEnregistrement = columnDateEnregistrement;
	}

	public String getColumnRegion() {
		return columnRegion;
	}

	public void setColumnRegion(String columnRegion) {
		this.columnRegion = columnRegion;
	}

	public String getColumnDistrict() {
		return columnDistrict;
	}

	public void setColumnDistrict(String columnDistrict) {
		this.columnDistrict = columnDistrict;
	}

	public String getColumnCDT() {
		return columnCDT;
	}

	public void setColumnCDT(String columnCDT) {
		this.columnCDT = columnCDT;
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

	public String getColumnIssuTraitement() {
		return columnIssuTraitement;
	}

	public void setColumnIssuTraitement(String columnIssuTraitement) {
		this.columnIssuTraitement = columnIssuTraitement;
	}

	public String getColumnID() {
		return columnID;
	}

	public void setColumnID(String columnID) {
		this.columnID = columnID;
	}

	public Date getColumnDateEnr() {
		return columnDateEnr;
	}

	public void setColumnDateEnr(Date columnDateEnr) {
		this.columnDateEnr = columnDateEnr;
	}

	public String getColumnTypePatient() {
		return columnTypePatient;
	}

	public void setColumnTypePatient(String columnTypePatient) {
		this.columnTypePatient = columnTypePatient;
	}

	public int getColumnMonth() {
		return columnMonth;
	}

	public void setColumnMonth(int columnMonth) {
		this.columnMonth = columnMonth;
	}
	
}
