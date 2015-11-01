package org.imogene.epicam.server.birtPojo;

public class TbLabRegister {

	private String columnAnneeRegistre;
	private String columnNumLab;
	private String columnDate;
	private String columnNom;
	private int columnAge;
	private String columnSexe;
	private String columnNomCDT;
	private String columnAdresse; //quartier, ville et téléphone
	//pour les examens
	private String columnDateExamenCrachat;
	private String columnRaisonExam;
	private String columnResultatExamenCrachat;

	private String columnMonth;

	private String columnDateSerologie;
	private String columnResultatSerologie;
	private String columnNombreCD4Serologie;
	
	
	private String columnNomCDTReference;
	private String columnPersonnel;
	private String columnObservations;
	
	public TbLabRegister() {
		super();
	}

	public TbLabRegister(String columnNumLab, String columnDate,
			String columnNom, int columnAge, String columnNomCDT,
			String columnAdresse, String columnRaisonExam,
			String columnNomCDTReference, String columnPersonnel,
			String columnObservations) {
		super();
		this.columnNumLab = columnNumLab;
		this.columnDate = columnDate;
		this.columnNom = columnNom;
		this.columnAge = columnAge;
		this.columnNomCDT = columnNomCDT;
		this.columnAdresse = columnAdresse;
		this.columnRaisonExam = columnRaisonExam;
		this.columnNomCDTReference = columnNomCDTReference;
		this.columnPersonnel = columnPersonnel;
		this.columnObservations = columnObservations;
	}

	public String getColumnNumLab() {
		return columnNumLab;
	}

	public void setColumnNumLab(String columnNumLab) {
		this.columnNumLab = columnNumLab;
	}

	public String getColumnDate() {
		return columnDate;
	}

	public void setColumnDate(String columnDate) {
		this.columnDate = columnDate;
	}

	public String getColumnNom() {
		return columnNom;
	}

	public void setColumnNom(String columnNom) {
		this.columnNom = columnNom;
	}

	public int getColumnAge() {
		return columnAge;
	}

	public void setColumnAge(int columnAge) {
		this.columnAge = columnAge;
	}

	public String getColumnNomCDT() {
		return columnNomCDT;
	}

	public void setColumnNomCDT(String columnNomCDT) {
		this.columnNomCDT = columnNomCDT;
	}

	public String getColumnAdresse() {
		return columnAdresse;
	}

	public void setColumnAdresse(String columnAdresse) {
		this.columnAdresse = columnAdresse;
	}

	public String getColumnRaisonExam() {
		return columnRaisonExam;
	}

	public void setColumnRaisonExam(String columnRaisonExam) {
		this.columnRaisonExam = columnRaisonExam;
	}

	public String getColumnNomCDTReference() {
		return columnNomCDTReference;
	}

	public void setColumnNomCDTReference(String columnNomCDTReference) {
		this.columnNomCDTReference = columnNomCDTReference;
	}

	public String getColumnPersonnel() {
		return columnPersonnel;
	}

	public void setColumnPersonnel(String columnPersonnel) {
		this.columnPersonnel = columnPersonnel;
	}

	public String getColumnObservations() {
		return columnObservations;
	}

	public void setColumnObservations(String columnObservations) {
		this.columnObservations = columnObservations;
	}

	public String getColumnAnneeRegistre() {
		return columnAnneeRegistre;
	}

	public void setColumnAnneeRegistre(String columnAnneeRegistre) {
		this.columnAnneeRegistre = columnAnneeRegistre;
	}

	public String getColumnSexe() {
		return columnSexe;
	}

	public void setColumnSexe(String columnSexe) {
		this.columnSexe = columnSexe;
	}

	public String getColumnDateExamenCrachat() {
		return columnDateExamenCrachat;
	}

	public void setColumnDateExamenCrachat(String columnDateExamenCrachat) {
		this.columnDateExamenCrachat = columnDateExamenCrachat;
	}

	public String getColumnResultatExamenCrachat() {
		return columnResultatExamenCrachat;
	}

	public void setColumnResultatExamenCrachat(String columnResultatExamenCrachat) {
		this.columnResultatExamenCrachat = columnResultatExamenCrachat;
	}

	public String getColumnDateSerologie() {
		return columnDateSerologie;
	}

	public void setColumnDateSerologie(String columnDateSerologie) {
		this.columnDateSerologie = columnDateSerologie;
	}

	public String getColumnResultatSerologie() {
		return columnResultatSerologie;
	}

	public void setColumnResultatSerologie(String columnResultatSerologie) {
		this.columnResultatSerologie = columnResultatSerologie;
	}

	public String getColumnNombreCD4Serologie() {
		return columnNombreCD4Serologie;
	}

	public void setColumnNombreCD4Serologie(String columnNombreCD4Serologie) {
		this.columnNombreCD4Serologie = columnNombreCD4Serologie;
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
