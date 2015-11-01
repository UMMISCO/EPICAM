package org.imogene.epicam.server.birtPojo;


public class TbRegister {
	
	
	private String columnAnneeRegistre;
	private String columnDateEnregistrement;
	private String columnNumRegLabo;
	private String columnNom;
	private String columnSexe;
	private int columnAge;
	private String columnAdresse; //quartier, ville et téléphone
	private String columnCDTMalade;
	private String columnDateDebutTrait;
	private String columnRegime;
	private String columnForme;
	private String columnType;
	private String columnTypeRelation;
	private String columnIssuTraitement;

	private String columnMonth;
	//Pour les examens

	private String columnDateExamenCrachat;
	private String columnResultatExamenCrachat;
	private String columnRaisonExamenCrachat;
	private String columnTechUtiliseExamenCrachat;
	
	private String columnDateSerologie;
	private String columnResultatSerologie;
	private String columnNombreCD4Serologie;
	private String columnCTMSerologie;
	private String columnARVSerologie;

	//Observations
	
	private String columnObservation;
	
	//Les traitements

	private String columnMedicaments;
	private String columnDateDebutMedicaments;
	private String columnDateFinMedicaments;
	
	
	public TbRegister(String columnDateEnregistrement, String columnNumRegLabo,
			String columnNom, String columnSexe, int columnAge,
			String columnAdresse, String columnCDTMalade,
			String columnDateDebutTrait, String columnRegime,
			String columnForme, String columnType, String columnIssuTraitement,
			String columnObservation) {
		super();
		this.columnDateEnregistrement = columnDateEnregistrement;
		this.columnNumRegLabo = columnNumRegLabo;
		this.columnNom = columnNom;
		this.columnSexe = columnSexe;
		this.columnAge = columnAge;
		this.columnAdresse = columnAdresse;
		this.columnCDTMalade = columnCDTMalade;
		this.columnDateDebutTrait = columnDateDebutTrait;
		this.columnRegime = columnRegime;
		this.columnForme = columnForme;
		this.columnType = columnType;
		this.columnIssuTraitement = columnIssuTraitement;
		this.columnObservation = columnObservation;
	}
	public TbRegister() {
		super();
	}
		
	public TbRegister(String columnAnneeRegistre,
			String columnDateEnregistrement, String columnNumRegLabo,
			String columnNom, String columnSexe, int columnAge,
			String columnAdresse, String columnCDTMalade,
			String columnDateDebutTrait, String columnRegime,
			String columnForme, String columnType, String columnIssuTraitement,
			String columnDateExamenCrachat, String columnResultatExamenCrachat,
			String columnRaisonExamenCrachat,
			String columnTechUtiliseExamenCrachat, String columnDateSerologie,
			String columnResultatSerologie, String columnNombreCD4Serologie,
			String columnCTMSerologie, String columnARVSerologie,
			String columnObservation, String columnMedicaments,
			String columnDateDebutMedicaments, String columnDateFinMedicaments) {
		super();
		this.columnAnneeRegistre = columnAnneeRegistre;
		this.columnDateEnregistrement = columnDateEnregistrement;
		this.columnNumRegLabo = columnNumRegLabo;
		this.columnNom = columnNom;
		this.columnSexe = columnSexe;
		this.columnAge = columnAge;
		this.columnAdresse = columnAdresse;
		this.columnCDTMalade = columnCDTMalade;
		this.columnDateDebutTrait = columnDateDebutTrait;
		this.columnRegime = columnRegime;
		this.columnForme = columnForme;
		this.columnType = columnType;
		this.columnIssuTraitement = columnIssuTraitement;
		this.columnDateExamenCrachat = columnDateExamenCrachat;
		this.columnResultatExamenCrachat = columnResultatExamenCrachat;
		this.columnRaisonExamenCrachat = columnRaisonExamenCrachat;
		this.columnTechUtiliseExamenCrachat = columnTechUtiliseExamenCrachat;
		this.columnDateSerologie = columnDateSerologie;
		this.columnResultatSerologie = columnResultatSerologie;
		this.columnNombreCD4Serologie = columnNombreCD4Serologie;
		this.columnCTMSerologie = columnCTMSerologie;
		this.columnARVSerologie = columnARVSerologie;
		this.columnObservation = columnObservation;
		this.columnMedicaments = columnMedicaments;
		this.columnDateDebutMedicaments = columnDateDebutMedicaments;
		this.columnDateFinMedicaments = columnDateFinMedicaments;
	}
	public TbRegister(String columnAnneeRegistre,
			String columnDateEnregistrement, String columnNumRegLabo,
			String columnNom, String columnSexe, int columnAge,
			String columnAdresse, String columnCDTMalade,
			String columnDateDebutTrait, String columnRegime,
			String columnForme, String columnType, String columnIssuTraitement,
			String columnDateExamenCrachat, String columnResultatExamenCrachat,
			String columnRaisonExamenCrachat,
			String columnTechUtiliseExamenCrachat, String columnDateSerologie,
			String columnResultatSerologie, String columnNombreCD4Serologie,
			String columnCTMSerologie, String columnARVSerologie,
			String columnObservation) {
		super();
		this.columnAnneeRegistre = columnAnneeRegistre;
		this.columnDateEnregistrement = columnDateEnregistrement;
		this.columnNumRegLabo = columnNumRegLabo;
		this.columnNom = columnNom;
		this.columnSexe = columnSexe;
		this.columnAge = columnAge;
		this.columnAdresse = columnAdresse;
		this.columnCDTMalade = columnCDTMalade;
		this.columnDateDebutTrait = columnDateDebutTrait;
		this.columnRegime = columnRegime;
		this.columnForme = columnForme;
		this.columnType = columnType;
		this.columnIssuTraitement = columnIssuTraitement;
		this.columnDateExamenCrachat = columnDateExamenCrachat;
		this.columnResultatExamenCrachat = columnResultatExamenCrachat;
		this.columnRaisonExamenCrachat = columnRaisonExamenCrachat;
		this.columnTechUtiliseExamenCrachat = columnTechUtiliseExamenCrachat;
		this.columnDateSerologie = columnDateSerologie;
		this.columnResultatSerologie = columnResultatSerologie;
		this.columnNombreCD4Serologie = columnNombreCD4Serologie;
		this.columnCTMSerologie = columnCTMSerologie;
		this.columnARVSerologie = columnARVSerologie;
		this.columnObservation = columnObservation;
	}
	public String getColumnDateEnregistrement() {
		return columnDateEnregistrement;
	}
	public void setColumnDateEnregistrement(String columnDateEnregistrement) {
		this.columnDateEnregistrement = columnDateEnregistrement;
	}
	public String getColumnNumRegLabo() {
		return columnNumRegLabo;
	}
	public void setColumnNumRegLabo(String columnNumRegLabo) {
		this.columnNumRegLabo = columnNumRegLabo;
	}
	public String getColumnNom() {
		return columnNom;
	}
	public void setColumnNom(String columnNom) {
		this.columnNom = columnNom;
	}
	public String getColumnSexe() {
		return columnSexe;
	}
	public void setColumnSexe(String columnSexe) {
		this.columnSexe = columnSexe;
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
	public String getColumnCDTMalade() {
		return columnCDTMalade;
	}
	public void setColumnCDTMalade(String columnCDTMalade) {
		this.columnCDTMalade = columnCDTMalade;
	}
	public String getColumnDateDebutTrait() {
		return columnDateDebutTrait;
	}
	public void setColumnDateDebutTrait(String columnDateDebutTrait) {
		this.columnDateDebutTrait = columnDateDebutTrait;
	}
	public String getColumnRegime() {
		return columnRegime;
	}
	public void setColumnRegime(String columnRegime) {
		this.columnRegime = columnRegime;
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
	public String getColumnIssuTraitement() {
		return columnIssuTraitement;
	}
	public void setColumnIssuTraitement(String columnIssuTraitement) {
		this.columnIssuTraitement = columnIssuTraitement;
	}
	public String getColumnObservation() {
		return columnObservation;
	}
	public void setColumnObservation(String columnObservation) {
		this.columnObservation = columnObservation;
	}
	public String getColumnAnneeRegistre() {
		return columnAnneeRegistre;
	}
	public void setColumnAnneeRegistre(String columnAnneeRegistre) {
		this.columnAnneeRegistre = columnAnneeRegistre;
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
	public String getColumnCTMSerologie() {
		return columnCTMSerologie;
	}
	public void setColumnCTMSerologie(String columnCTMSerologie) {
		this.columnCTMSerologie = columnCTMSerologie;
	}
	public String getColumnARVSerologie() {
		return columnARVSerologie;
	}
	public void setColumnARVSerologie(String columnARVSerologie) {
		this.columnARVSerologie = columnARVSerologie;
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
