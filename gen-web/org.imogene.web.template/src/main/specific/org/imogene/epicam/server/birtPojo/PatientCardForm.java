package org.imogene.epicam.server.birtPojo;

import java.util.Date;

public class PatientCardForm {

	private String columnRegion;
	private String columnDistrict;
	
	private String columnNom;
	private String numRegTB;
	private String columnAdresse;
	private String columnSexe;
	private String columnAge;
	private String columnDateDebutTrait;
	private String columnNumRegTB;
	private String columnNomCDT;
	private String columnFormeMaladie;
	private String columnTypePatient;
	
	//Pour les examens
	private String columnDateExamenCrachat;
	private String columnResultatExamenCrachat;
	private String columnRaisonExamenCrachat;
	private String columnTechUtiliseExamenCrachat;
	
	private double columnPoidsPatient;
	private String columnDataPoidPatient;
	
	//Pour les traitements

	private String columnRegimePhaseInitiale;
	private String columnMedicamentsPhaseInitiale;
	private String columnDateEffectivePhaseInitiale;
	private String columnDateFinMedicamentsPhaseInitiale;
	private int columnQuantiteMedicamentPhaseInitiale;

	private String columnRegimePhaseContinuation;
	private String columnMedicamentsPhaseContinuation;
	private String columnDateEffectivePhaseContinuation;
	private String columnDateFinMedicamentsPhaseContinuation;
	private int columnQuantiteMedicamentPhaseContinuation;
	
	private String columnCDT;
		
	private String columnMonth;
	
	Date columnDateRDV;
	String columnHonoree;
	
	private String columnObservations;
	
	public PatientCardForm() {
		super();
	}

	public PatientCardForm(String columnNom, String columnAdresse,
			String columnSexe, String columnAge, String columnDateDebutTrait,
			String columnNumRegTB, String columnNomCDT,
			String columnFormeMaladie, String columnTypePatient,
			String columnObservations) {
		super();
		this.columnNom = columnNom;
		this.columnAdresse = columnAdresse;
		this.columnSexe = columnSexe;
		this.columnAge = columnAge;
		this.columnDateDebutTrait = columnDateDebutTrait;
		this.columnNumRegTB = columnNumRegTB;
		this.columnNomCDT = columnNomCDT;
		this.columnFormeMaladie = columnFormeMaladie;
		this.columnTypePatient = columnTypePatient;
		this.columnObservations = columnObservations;
	}

	public String getColumnNom() {
		return columnNom;
	}

	public void setColumnNom(String columnNom) {
		this.columnNom = columnNom;
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

	public String getColumnAdresse() {
		return columnAdresse;
	}

	public void setColumnAdresse(String columnAdresse) {
		this.columnAdresse = columnAdresse;
	}

	public String getColumnSexe() {
		return columnSexe;
	}

	public void setColumnSexe(String columnSexe) {
		this.columnSexe = columnSexe;
	}

	public String getColumnAge() {
		return columnAge;
	}

	public String getColumnCDT() {
		return columnCDT;
	}

	public void setColumnCDT(String columnCDT) {
		this.columnCDT = columnCDT;
	}

	public void setColumnMonth(String columnMonth) {
		this.columnMonth = columnMonth;
	}

	public void setColumnAge(String columnAge) {
		this.columnAge = columnAge;
	}

	public String getColumnDateDebutTrait() {
		return columnDateDebutTrait;
	}

	public void setColumnDateDebutTrait(String columnDateDebutTrait) {
		this.columnDateDebutTrait = columnDateDebutTrait;
	}

	public String getColumnNumRegTB() {
		return columnNumRegTB;
	}

	public void setColumnNumRegTB(String columnNumRegTB) {
		this.columnNumRegTB = columnNumRegTB;
	}

	public String getColumnNomCDT() {
		return columnNomCDT;
	}

	public void setColumnNomCDT(String columnNomCDT) {
		this.columnNomCDT = columnNomCDT;
	}

	public String getColumnFormeMaladie() {
		return columnFormeMaladie;
	}

	public void setColumnFormeMaladie(String columnFormeMaladie) {
		this.columnFormeMaladie = columnFormeMaladie;
	}

	public String getColumnTypePatient() {
		return columnTypePatient;
	}

	public void setColumnTypePatient(String columnTypePatient) {
		this.columnTypePatient = columnTypePatient;
	}

	public String getColumnObservations() {
		return columnObservations;
	}

	public void setColumnObservations(String columnObservations) {
		this.columnObservations = columnObservations;
	}

	public String getNumRegTB() {
		return numRegTB;
	}

	public void setNumRegTB(String numRegTB) {
		this.numRegTB = numRegTB;
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

	public double getColumnPoidsPatient() {
		return columnPoidsPatient;
	}

	public void setColumnPoidsPatient(double poidsPatient) {
		this.columnPoidsPatient = poidsPatient;
	}

	public String getColumnDataPoidPatient() {
		return columnDataPoidPatient;
	}

	public void setColumnDataPoidPatient(String dataPoidPatient) {
		this.columnDataPoidPatient = dataPoidPatient;
	}

	public String getColumnRegimePhaseInitiale() {
		return columnRegimePhaseInitiale;
	}

	public void setColumnRegimePhaseInitiale(String columnRegimePhaseInitiale) {
		this.columnRegimePhaseInitiale = columnRegimePhaseInitiale;
	}

	public String getColumnMedicamentsPhaseInitiale() {
		return columnMedicamentsPhaseInitiale;
	}

	public void setColumnMedicamentsPhaseInitiale(
			String columnMedicamentsPhaseInitiale) {
		this.columnMedicamentsPhaseInitiale = columnMedicamentsPhaseInitiale;
	}

	public String getColumnDateEffectivePhaseInitiale() {
		return columnDateEffectivePhaseInitiale;
	}

	public void setColumnDateEffectivePhaseInitiale(
			String columnDateDebutMedicamentsPhaseInitiale) {
		this.columnDateEffectivePhaseInitiale = columnDateDebutMedicamentsPhaseInitiale;
	}

	public String getColumnDateFinMedicamentsPhaseInitiale() {
		return columnDateFinMedicamentsPhaseInitiale;
	}

	public void setColumnDateFinMedicamentsPhaseInitiale(
			String columnDateFinMedicamentsPhaseInitiale) {
		this.columnDateFinMedicamentsPhaseInitiale = columnDateFinMedicamentsPhaseInitiale;
	}

	public int getColumnQuantiteMedicamentPhaseInitiale() {
		return columnQuantiteMedicamentPhaseInitiale;
	}

	public void setColumnQuantiteMedicamentPhaseInitiale(
			int columnQuantiteMedicamentPhaseInitiale) {
		this.columnQuantiteMedicamentPhaseInitiale = columnQuantiteMedicamentPhaseInitiale;
	}

	public String getColumnRegimePhaseContinuation() {
		return columnRegimePhaseContinuation;
	}

	public void setColumnRegimePhaseContinuation(
			String columnRegimePhaseContinuation) {
		this.columnRegimePhaseContinuation = columnRegimePhaseContinuation;
	}

	public String getColumnMedicamentsPhaseContinuation() {
		return columnMedicamentsPhaseContinuation;
	}

	public void setColumnMedicamentsPhaseContinuation(
			String columnMedicamentsPhaseContinuation) {
		this.columnMedicamentsPhaseContinuation = columnMedicamentsPhaseContinuation;
	}

	public String getColumnDateEffectivePhaseContinuation() {
		return columnDateEffectivePhaseContinuation;
	}

	public void setColumnDateEffectivePhaseContinuation(
			String columnDateDebutMedicamentsPhaseContinuation) {
		this.columnDateEffectivePhaseContinuation = columnDateDebutMedicamentsPhaseContinuation;
	}

	public String getColumnDateFinMedicamentsPhaseContinuation() {
		return columnDateFinMedicamentsPhaseContinuation;
	}

	public void setColumnDateFinMedicamentsPhaseContinuation(
			String columnDateFinMedicamentsPhaseContinuation) {
		this.columnDateFinMedicamentsPhaseContinuation = columnDateFinMedicamentsPhaseContinuation;
	}

	public int getColumnQuantiteMedicamentPhaseContinuation() {
		return columnQuantiteMedicamentPhaseContinuation;
	}

	public void setColumnQuantiteMedicamentPhaseContinuation(
			int columnQuantiteMedicamentPhaseContinuation) {
		this.columnQuantiteMedicamentPhaseContinuation = columnQuantiteMedicamentPhaseContinuation;
	}
	public String getColumnMonth() {
		return columnMonth;
	}

	public Date getColumnDateRDV() {
		return columnDateRDV;
	}

	public void setColumnDateRDV(Date columnDateRDV) {
		this.columnDateRDV = columnDateRDV;
	}

	public String getColumnHonoree() {
		return columnHonoree;
	}

	public void setColumnHonoree(String columnHonoree) {
		this.columnHonoree = columnHonoree;
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
