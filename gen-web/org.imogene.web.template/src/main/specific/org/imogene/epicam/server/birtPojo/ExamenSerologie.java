package org.imogene.epicam.server.birtPojo;

public class ExamenSerologie {


	private String columnNumRegLaboPatient;
	private String columnResultat;
	private String columnDate;
	private String columnNombreCD4;
	private String columnCTM;
	private String columnARV;
	public ExamenSerologie(String columnResultat, String columnDate,
			String columnNombreCD4, String columnCTM, String columnARV) {
		super();
		this.columnResultat = columnResultat;
		this.columnDate = columnDate;
		this.columnNombreCD4 = columnNombreCD4;
		this.columnCTM = columnCTM;
		this.columnARV = columnARV;
	}
	public ExamenSerologie() {
		super();
	}
	public String getColumnResultat() {
		return columnResultat;
	}
	public void setColumnResultat(String columnResultat) {
		this.columnResultat = columnResultat;
	}
	public String getColumnDate() {
		return columnDate;
	}
	public void setColumnDate(String columnDate) {
		this.columnDate = columnDate;
	}
	public String getColumnNombreCD4() {
		return columnNombreCD4;
	}
	public void setColumnNombreCD4(String columnNombreCD4) {
		this.columnNombreCD4 = columnNombreCD4;
	}
	public String getColumnCTM() {
		return columnCTM;
	}
	public void setColumnCTM(String columnCTM) {
		this.columnCTM = columnCTM;
	}
	public String getColumnARV() {
		return columnARV;
	}
	public void setColumnARV(String columnARV) {
		this.columnARV = columnARV;
	}
	
	
}
