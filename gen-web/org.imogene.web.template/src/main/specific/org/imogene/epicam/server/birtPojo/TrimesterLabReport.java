package org.imogene.epicam.server.birtPojo;

import java.util.Date;

public class TrimesterLabReport {

	private String columnMonth;

	String columnID;
	Date columnDateExamen;
	String columnRaisonExamenATB;
	String columnResultatExamenATB;
	String columnResultatExamenBAAR;
	String columnRaisonExamenBAAR;
	String columnResultatSerologie;
	String columnRaisonExamenSerologie;

	int columnNBExamenBAAR;
	
	String columnCDT;
	int columnNBDespistageBAAR;
	int columnNBDepistageBAARPositif;
	int columnNBDepistageBAARNegatif;
	int columnNBDepistageBAARRare;


	int columnNBSuiviBAAR;
	int columnNBSuiviBAARPositif;
	int columnNBSuiviBAARNegatif;
	int columnNBSuiviBAARRare;
	
	
	public TrimesterLabReport() {
		super();
	}
	public TrimesterLabReport(String columnID, Date columnDateExamen,
			String columnResultatExamenATB, String columnExamenBAAR,
			String columnSerologie) {
		super();
		this.columnID = columnID;
		this.columnDateExamen = columnDateExamen;
		this.columnResultatExamenATB = columnResultatExamenATB;
		this.columnResultatExamenBAAR = columnExamenBAAR;
		this.columnResultatSerologie = columnSerologie;
	}
	public String getColumnID() {
		return columnID;
	}
	public void setColumnID(String columnID) {
		this.columnID = columnID;
	}
	public Date getColumnDateExamen() {
		return columnDateExamen;
	}
	public void setColumnDateExamen(Date columnDateExamen) {
		this.columnDateExamen = columnDateExamen;
	}
	public String getColumnResultatExamenATB() {
		return columnResultatExamenATB;
	}
	public void setColumnResultatExamenATB(String columnResultatExamenATB) {
		this.columnResultatExamenATB = columnResultatExamenATB;
	}
	public String getColumnResultatExamenBAAR() {
		return columnResultatExamenBAAR;
	}
	public void setColumnResultatExamenBAAR(String columnExamenBAAR) {
		this.columnResultatExamenBAAR = columnExamenBAAR;
	}
	public String getColumnResultatSerologie() {
		return columnResultatSerologie;
	}
	public void setColumnResultatSerologie(String columnSerologie) {
		this.columnResultatSerologie = columnSerologie;
	}
	public String getColumnRaisonExamenATB() {
		return columnRaisonExamenATB;
	}
	public void setColumnRaisonExamenATB(String columnRaisonExamenATB) {
		this.columnRaisonExamenATB = columnRaisonExamenATB;
	}
	public String getColumnRaisonExamenBAAR() {
		return columnRaisonExamenBAAR;
	}
	public void setColumnRaisonExamenBAAR(String columnRaisonExamenBAAR) {
		this.columnRaisonExamenBAAR = columnRaisonExamenBAAR;
	}
	public String getColumnRaisonExamenSerologie() {
		return columnRaisonExamenSerologie;
	}
	public void setColumnRaisonExamenSerologie(String columnRaisonExamenSerologie) {
		this.columnRaisonExamenSerologie = columnRaisonExamenSerologie;
	}

	public String getColumnCDT() {
		return columnCDT;
	}
	public void setColumnCDT(String columnCDT) {
		this.columnCDT = columnCDT;
	}
	public int getColumnNBDepistageBAARPositif() {
		return columnNBDepistageBAARPositif;
	}
	public void setColumnNBDepistageBAARPositif(int columnNBDepistageBAARPositif) {
		this.columnNBDepistageBAARPositif = columnNBDepistageBAARPositif;
	}
	public int getColumnNBDepistageBAARNegatif() {
		return columnNBDepistageBAARNegatif;
	}
	public void setColumnNBDepistageBAARNegatif(int columnNBDepistageBAARNegatif) {
		this.columnNBDepistageBAARNegatif = columnNBDepistageBAARNegatif;
	}
	public int getColumnNBDepistageBAARRare() {
		return columnNBDepistageBAARRare;
	}
	public void setColumnNBDepistageBAARRare(int columnNBDepistageBAARRare) {
		this.columnNBDepistageBAARRare = columnNBDepistageBAARRare;
	}
	public int getColumnNBSuiviBAAR() {
		return columnNBSuiviBAAR;
	}
	public void setColumnNBSuiviBAAR(int columnNBSuiviBAAR) {
		this.columnNBSuiviBAAR = columnNBSuiviBAAR;
	}
	public int getColumnNBSuiviBAARPositif() {
		return columnNBSuiviBAARPositif;
	}
	public int getNbExamenBAAR() {
		return columnNBExamenBAAR;
	}
	public void setNbExamenBAAR(int nbExamenBAAR) {
		this.columnNBExamenBAAR = nbExamenBAAR;
	}
	public int getColumnNBDespistageBAAR() {
		return columnNBDespistageBAAR;
	}
	public int getColumnNBExamenBAAR() {
		return columnNBExamenBAAR;
	}
	public void setColumnNBExamenBAAR(int columnNBExamenBAAR) {
		this.columnNBExamenBAAR = columnNBExamenBAAR;
	}
	public void setColumnNBDespistageBAAR(int columnNBDespistageBAAR) {
		this.columnNBDespistageBAAR = columnNBDespistageBAAR;
	}
	public void setColumnNBSuiviBAARPositif(int columnNBSuiviBAARPositif) {
		this.columnNBSuiviBAARPositif = columnNBSuiviBAARPositif;
	}
	public int getColumnNBSuiviBAARNegatif() {
		return columnNBSuiviBAARNegatif;
	}
	public void setColumnNBSuiviBAARNegatif(int columnNBSuiviBAARNegatif) {
		this.columnNBSuiviBAARNegatif = columnNBSuiviBAARNegatif;
	}
	public int getColumnNBSuiviBAARRare() {
		return columnNBSuiviBAARRare;
	}
	public void setColumnNBSuiviBAARRare(int columnNBSuiviBAARRare) {
		this.columnNBSuiviBAARRare = columnNBSuiviBAARRare;
	}
	public String getColumnMonth() {
		return columnMonth;
	}

	public void setColumnMonth(int columnMonth) {

		String month="";
		switch (columnMonth) {
		case 0:
			month = "01_Jan";
			break;
		case 1:
			month = "02_Feb";
			break;
		case 2:
			month = "03_Mar";
			break;
		case 3:
			month = "04_Apr";
			break;
		case 4:
			month = "05_May";
			break;
		case 5:
			month = "06_Jun";
			break;
		case 6:
			month = "07_Jul";
			break;
		case 7:
			month = "08_Aug";
			break;
		case 8:
			month = "09_Sep";
			break;
		case 9:
			month = "10_Oct";
			break;
		case 10:
			month = "11_Nov";
			break;
		case 11:
			month = "12_Dec";
			break;
		}
		this.columnMonth = month;
	}
}
