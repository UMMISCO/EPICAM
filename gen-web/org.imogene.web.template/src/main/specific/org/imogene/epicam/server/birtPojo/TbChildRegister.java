package org.imogene.epicam.server.birtPojo;

public class TbChildRegister {
	
	
	
	private String columnAnneeRegistre;
	private String columnNumero;
	private String columnDate;
	private String columnNom;
//	private String columnNomCasIndex;
	private String columnNomCasIndex;
	private String columnTypeCasIndex;
	private String columnTypeRelation;
	private String columnSexe;
	private int columnAge;
	private String columnNomCDT;
	private String columnDateDebutTrait;
	private String columnAdresse;
	private String columnIssuTraitement;

//	private String columnRegime;
	private String columnForme;
	private String columnType;
//	private String columnIssuTraitement;

	//Pour les examens

	private String columnDateExamenCrachat;
	private String columnResultatExamenCrachat;
	private String columnRaisonExamenCrachat;
	private String columnTechUtiliseExamenCrachat;

	//Les traitements

	private String columnRegime;
	private String columnMedicaments;
	private String columnDateDebutMedicaments;
	private String columnDateFinMedicaments;
	

	private String columnMonth;
	
	//Les observations
	private String columnObservations;
	
	public TbChildRegister() {
		super();
	}

	public TbChildRegister(String columnAnneeRegistre, String columnNumero,
			String columnDate, String columnNom, String columnNomCasIndex,
			int columnAge, String columnAdresse,
			String columnIssuTraitement, String columnObservations) {
		super();
		this.columnAnneeRegistre = columnAnneeRegistre;
		this.columnNumero = columnNumero;
		this.columnDate = columnDate;
		this.columnNom = columnNom;
		this.columnNomCasIndex = columnNomCasIndex;
		this.columnAge = columnAge;
		this.columnAdresse = columnAdresse;
		this.columnIssuTraitement = columnIssuTraitement;
		this.columnObservations = columnObservations;
	}

	public TbChildRegister(String columnAnneeRegistre, String columnNumero,
			String columnDate, String columnNom, String columnNomCasIndex,
			int columnAge, String columnAdresse,
			String columnIssuTraitement, String columnDateExamenCrachat,
			String columnResultatExamenCrachat,
			String columnRaisonExamenCrachat,
			String columnTechUtiliseExamenCrachat, String columnRegime,
			String columnMedicaments, String columnDateDebutMedicaments,
			String columnDateFinMedicaments, String columnObservations) {
		super();
		this.columnAnneeRegistre = columnAnneeRegistre;
		this.columnNumero = columnNumero;
		this.columnDate = columnDate;
		this.columnNom = columnNom;
		this.columnNomCasIndex = columnNomCasIndex;
		this.columnAge = columnAge;
		this.columnAdresse = columnAdresse;
		this.columnIssuTraitement = columnIssuTraitement;
		this.columnDateExamenCrachat = columnDateExamenCrachat;
		this.columnResultatExamenCrachat = columnResultatExamenCrachat;
		this.columnRaisonExamenCrachat = columnRaisonExamenCrachat;
		this.columnTechUtiliseExamenCrachat = columnTechUtiliseExamenCrachat;
		this.columnRegime = columnRegime;
		this.columnMedicaments = columnMedicaments;
		this.columnDateDebutMedicaments = columnDateDebutMedicaments;
		this.columnDateFinMedicaments = columnDateFinMedicaments;
		this.columnObservations = columnObservations;
	}

	public String getColumnAnneeRegistre() {
		return columnAnneeRegistre;
	}

	public void setColumnAnneeRegistre(String columnAnneeRegistre) {
		this.columnAnneeRegistre = columnAnneeRegistre;
	}

	public String getColumnNumero() {
		return columnNumero;
	}

	public void setColumnNumero(String columnNumero) {
		this.columnNumero = columnNumero;
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

	public String getColumnAdresse() {
		return columnAdresse;
	}

	public void setColumnAdresse(String columnAdresse) {
		this.columnAdresse = columnAdresse;
	}

	public String getColumnIssuTraitement() {
		return columnIssuTraitement;
	}

	public void setColumnIssuTraitement(String columnIssuTraitement) {
		this.columnIssuTraitement = columnIssuTraitement;
	}

	public String getColumnObservations() {
		return columnObservations;
	}

	public void setColumnObservations(String columnObservations) {
		this.columnObservations = columnObservations;
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

	public String getColumnRaisonExamenCrachat() {
		return columnRaisonExamenCrachat;
	}

	public void setColumnRaisonExamenCrachat(String columnRaisonExamenCrachat) {
		this.columnRaisonExamenCrachat = columnRaisonExamenCrachat;
	}

	public String getColumnTechUtiliseExamenCrachat() {
		return columnTechUtiliseExamenCrachat;
	}

	public void setColumnTechUtiliseExamenCrachat(
			String columnTechUtiliseExamenCrachat) {
		this.columnTechUtiliseExamenCrachat = columnTechUtiliseExamenCrachat;
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

	public String getColumnDateDebutMedicaments() {
		return columnDateDebutMedicaments;
	}

	public void setColumnDateDebutMedicaments(String columnDateDebutMedicaments) {
		this.columnDateDebutMedicaments = columnDateDebutMedicaments;
	}

	public String getColumnDateFinMedicaments() {
		return columnDateFinMedicaments;
	}

	public void setColumnDateFinMedicaments(String columnDateFinMedicaments) {
		this.columnDateFinMedicaments = columnDateFinMedicaments;
	}

	public String getColumnSexe() {
		return columnSexe;
	}

	public void setColumnSexe(String columnSexe) {
		this.columnSexe = columnSexe;
	}

	public String getColumnNomCDT() {
		return columnNomCDT;
	}

	public void setColumnNomCDT(String columnNomCDT) {
		this.columnNomCDT = columnNomCDT;
	}

	public String getColumnDateDebutTrait() {
		return columnDateDebutTrait;
	}

	public void setColumnDateDebutTrait(String columnDateDebutTrait) {
		this.columnDateDebutTrait = columnDateDebutTrait;
	}

	public String getColumnForme() {
		return columnForme;
	}

	public void setColumnForme(String columnForme) {
		this.columnForme = columnForme;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnNomCasIndex() {
		return columnNomCasIndex;
	}

	public void setColumnNomCasIndex(String columnNomCasIndex) {
		this.columnNomCasIndex = columnNomCasIndex;
	}

	public String getColumnTypeCasIndex() {
		return columnTypeCasIndex;
	}

	public void setColumnTypeCasIndex(String columnTypeCasIndex) {
		this.columnTypeCasIndex = columnTypeCasIndex;
	}

	public String getColumnTypeRelation() {
		return columnTypeRelation;
	}
	public void setColumnTypeRelation(String typeRelation) {
		this.columnTypeRelation = typeRelation;
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
