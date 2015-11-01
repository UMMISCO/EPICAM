package org.imogene.epicam.server.birtPojo;

import java.util.Date;

public class TrimesterDetectReport {

	private String columnMonth;
	/*tb cases registered*/
	
	private String columnRegion;
	private String columnDistrict;
	private String columnCDT;
//	private String columnTrimestre; Plus besoin cat c'est calculé
	private String columnAnnee;
	private String columnPeriodeDebut;
	private String columnPeriodeFin;
	private Date columnDateEnregistrement;
	
	private String columntrancheAge;
	private String columnreadableSexe;
	
	private String columnNumTB;
	private int columnAge;
	private String columnSexe;
	private String columnTypePatient;
	private String columnFormeMaladie;
	private String columnSerologie;
	
	//For statistics
	private int columnNouveauCas;
	private int columnRechuttes;
	private int columnEchecs;
	private int columnRepriseTrait;
	private int columnTpmMoinsInf15;
	private int columnTpmMoinsSup15;
	private int columnTep;
	private int columnTotalDesCas;
	private int columnCas0_4;
	private int columnCas5_14;
	private int columnVihTPMPlusNouveauxFait;
	private int columnVihTPMPlusPositif;
	
	
	private String columnResultatExamenCrachas;
	
	public TrimesterDetectReport() {
		super();
	}

	public TrimesterDetectReport(String columnRegion, String columnDistrict,
			String columnCDT, String columnTrimestre, String columnAnnee,
			String columnPeriodeDebut, String columnPeriodeFin, int columnAge,
			String columnSexe, String columnTypePatient,
			String columnFormeMaladie, String columnSerologie) {
		super();
		this.columnRegion = columnRegion;
		this.columnDistrict = columnDistrict;
		this.columnCDT = columnCDT;
//		this.columnTrimestre = columnTrimestre;
		this.columnAnnee = columnAnnee;
		this.columnPeriodeDebut = columnPeriodeDebut;
		this.columnPeriodeFin = columnPeriodeFin;
		this.columnAge = columnAge;
		this.columnSexe = columnSexe;
		this.columnTypePatient = columnTypePatient;
		this.columnFormeMaladie = columnFormeMaladie;
		this.columnSerologie = columnSerologie;
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

//	public String getColumnTrimestre() {
//		return columnTrimestre;
//	}
//
//	public void setColumnTrimestre(String columnTrimestre) {
//		this.columnTrimestre = columnTrimestre;
//	}

	public String getColumnAnnee() {
		return columnAnnee;
	}

	public void setColumnAnnee(String columnAnnee) {
		this.columnAnnee = columnAnnee;
	}

	public String getColumnPeriodeDebut() {
		return columnPeriodeDebut;
	}

	public void setColumnPeriodeDebut(String columnPeriodeDebut) {
		this.columnPeriodeDebut = columnPeriodeDebut;
	}

	public String getColumnPeriodeFin() {
		return columnPeriodeFin;
	}

	public void setColumnPeriodeFin(String columnPeriodeFin) {
		this.columnPeriodeFin = columnPeriodeFin;
	}

	public int getColumnAge() {
		return columnAge;
	}

	public void setColumnAge(int columnAge) {
		this.columnAge = columnAge;
	}

	public String getColumnSexe() {
		return columnSexe;
	}

	public void setColumnSexe(String columnSexe) {
		this.columnSexe = columnSexe;
	}

	public String getColumnTypePatient() {
		return columnTypePatient;
	}

	public void setColumnTypePatient(String columnTypePatient) {
		this.columnTypePatient = columnTypePatient;
	}

	public String getColumnFormeMaladie() {
		return columnFormeMaladie;
	}

	public void setColumnFormeMaladie(String columnFormeMaladie) {
		this.columnFormeMaladie = columnFormeMaladie;
	}

	public String getColumnSerologie() {
		return columnSerologie;
	}

	public void setColumnSerologie(String columnSerologie) {
		this.columnSerologie = columnSerologie;
	}

	public Date getColumnDateEnregistrement() {
		return columnDateEnregistrement;
	}

	public void setColumnDateEnregistrement(Date columnDateEnregistrement) {
		this.columnDateEnregistrement = columnDateEnregistrement;
	}

	public String getColumnResultatExamenCrachas() {
		return columnResultatExamenCrachas;
	}

	public void setColumnResultatExamenCrachas(String columnResultatExamenCrachas) {
		this.columnResultatExamenCrachas = columnResultatExamenCrachas;
	}

	public String getColumnNumTB() {
		return columnNumTB;
	}

	public void setColumnNumTB(String columnNumTB) {
		this.columnNumTB = columnNumTB;
	}

	public int getColumnNouveauCas() {
		return columnNouveauCas;
	}

	public void setColumnNouveauCas(int nouveauCas) {
		this.columnNouveauCas = nouveauCas;
	}

	public int getColumnRechuttes() {
		return columnRechuttes;
	}

	public void setColumnRechuttes(int rechuttes) {
		this.columnRechuttes = rechuttes;
	}

	public int getColumnEchecs() {
		return columnEchecs;
	}

	public void setColumnEchecs(int echecs) {
		this.columnEchecs = echecs;
	}

	public int getColumnRepriseTrait() {
		return columnRepriseTrait;
	}

	public void setColumnRepriseTrait(int repriseTrait) {
		this.columnRepriseTrait = repriseTrait;
	}

	public int getColumnTpmMoinsInf15() {
		return columnTpmMoinsInf15;
	}

	public void setColumnTpmMoinsInf15(int tpmMoinsInf15) {
		this.columnTpmMoinsInf15 = tpmMoinsInf15;
	}

	public int getColumnTpmMoinsSup15() {
		return columnTpmMoinsSup15;
	}

	public void setColumnTpmMoinsSup15(int tpmMoinsSup15) {
		this.columnTpmMoinsSup15 = tpmMoinsSup15;
	}

	public int getColumnTep() {
		return columnTep;
	}

	public void setColumnTep(int tep) {
		this.columnTep = tep;
	}

	public int getColumnTotalDesCas() {
		return columnTotalDesCas;
	}

	public void setColumnTotalDesCas(int totalDesCas) {
		this.columnTotalDesCas = totalDesCas;
	}

	public int getColumnCas0_4() {
		return columnCas0_4;
	}

	public void setColumnCas0_4(int cas0_4) {
		this.columnCas0_4 = cas0_4;
	}

	public int getColumnCas5_14() {
		return columnCas5_14;
	}

	public void setColumnCas5_14(int cas5_14) {
		this.columnCas5_14 = cas5_14;
	}

	public int getColumnVihTPMPlusNouveauxFait() {
		return columnVihTPMPlusNouveauxFait;
	}

	public void setColumnVihTPMPlusNouveauxFait(int vihTPMPlusNouveauxFait) {
		this.columnVihTPMPlusNouveauxFait = vihTPMPlusNouveauxFait;
	}

	public int getColumnVihTPMPlusPositif() {
		return columnVihTPMPlusPositif;
	}

	public void setColumnVihTPMPlusPositif(int vihTPMPlusPositif) {
		this.columnVihTPMPlusPositif = vihTPMPlusPositif;
	}

	public String getColumnTrancheAge() {
		return columntrancheAge;
	}

	public void setColumnTrancheAge(int age) {
		String tranche = null;
		if(age>0 && age <=14){
			tranche = "0 - 14";
		}
		if(age>14 && age <=24){
			tranche = "15 - 24";			
		}
		if(age>0 && age <=14){
			tranche = "0 - 14";			
		}
		if(age>24 && age <=34){
			tranche = "25 - 34";			
		}
		if(age>34 && age <=44){
			tranche = "35 - 44";			
		}
		if(age>44 && age <=54){
			tranche = "55 - 64";			
		}
		if(age>54){
			tranche = "64 - +";			
		}
		
		this.columntrancheAge = tranche;
	}

	public String getColumnReadableSexe() {
		return columnreadableSexe;
	}

	public void setColumnReadableSexe(String sexe) {

		if(sexe.equals("0")){
			this.columnreadableSexe = "M";
		}

		if(sexe.equals("1")){
			this.columnreadableSexe = "F";
		}
		
		
	}

	public void setColumnTrancheAge(String trancheAge) {
		this.columntrancheAge = trancheAge;
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
