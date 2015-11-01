package org.imogene.epicam.server.birtPojo;

public class TreatmentForm {
	

	private int columnMonth;
	
	private String columnNumRegTB;
	private String columnNom;
	private String columnSexe;
	private String columnAdresse;
	private String columnCDT;
	private String columnNumTB;
	private String columnFormeMaladie;
	private String columnTypePatient;
	private String columnPatReTraitNumTBPreced;
	private String columnUtiliseTabac;
	private String columnVeutArreter30ProJours;
	private String columnObservations;
	private String columnDecisionArretTratit;
	
	

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
	
	public TreatmentForm() {
		super();
	}

	public TreatmentForm(String columnNom, String columnSexe,
			String columnAdresse, String columnCDT, String columnNumTB,
			String columnFormeMaladie, String columnTypePatient,
			String columnPatReTraitNumTBPreced, String columnUtiliseTabac,
			String columnVeutArreter30ProJours, String columnObservations,
			String columnDecisionArretTratit) {
		super();
		this.columnNom = columnNom;
		this.columnSexe = columnSexe;
		this.columnAdresse = columnAdresse;
		this.columnCDT = columnCDT;
		this.columnNumTB = columnNumTB;
		this.columnFormeMaladie = columnFormeMaladie;
		this.columnTypePatient = columnTypePatient;
		this.columnPatReTraitNumTBPreced = columnPatReTraitNumTBPreced;
		this.columnUtiliseTabac = columnUtiliseTabac;
		this.columnVeutArreter30ProJours = columnVeutArreter30ProJours;
		this.columnObservations = columnObservations;
		this.columnDecisionArretTratit = columnDecisionArretTratit;
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

	public String getColumnAdresse() {
		return columnAdresse;
	}

	public void setColumnAdresse(String columnAdresse) {
		this.columnAdresse = columnAdresse;
	}

	public String getColumnCDT() {
		return columnCDT;
	}

	public void setColumnCDT(String columnCDT) {
		this.columnCDT = columnCDT;
	}

	public String getColumnNumTB() {
		return columnNumTB;
	}

	public void setColumnNumTB(String columnNumTB) {
		this.columnNumTB = columnNumTB;
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

	public String getColumnPatReTraitNumTBPreced() {
		return columnPatReTraitNumTBPreced;
	}

	public void setColumnPatReTraitNumTBPreced(String columnPatReTraitNumTBPreced) {
		this.columnPatReTraitNumTBPreced = columnPatReTraitNumTBPreced;
	}

	public String getColumnUtiliseTabac() {
		return columnUtiliseTabac;
	}

	public void setColumnUtiliseTabac(String columnUtiliseTabac) {
		this.columnUtiliseTabac = columnUtiliseTabac;
	}

	public String getColumnVeutArreter30ProJours() {
		return columnVeutArreter30ProJours;
	}

	public void setColumnVeutArreter30ProJours(String columnVeutArreter30ProJours) {
		this.columnVeutArreter30ProJours = columnVeutArreter30ProJours;
	}

	public String getColumnObservations() {
		return columnObservations;
	}

	public void setColumnObservations(String columnObservations) {
		this.columnObservations = columnObservations;
	}

	public String getColumnDecisionArretTratit() {
		return columnDecisionArretTratit;
	}

	public void setColumnDecisionArretTratit(String columnDecisionArretTratit) {
		this.columnDecisionArretTratit = columnDecisionArretTratit;
	}

	public String getColumnNumRegTB() {
		return columnNumRegTB;
	}

	public void setColumnNumRegTB(String columnNumRegTB) {
		this.columnNumRegTB = columnNumRegTB;
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

	public void setColumnPoidsPatient(double columnPoidsPatient) {
		this.columnPoidsPatient = columnPoidsPatient;
	}

	public String getColumnDataPoidPatient() {
		return columnDataPoidPatient;
	}

	public void setColumnDataPoidPatient(String columnDataPoidPatient) {
		this.columnDataPoidPatient = columnDataPoidPatient;
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
			String columnDateEffectivePhaseInitiale) {
		this.columnDateEffectivePhaseInitiale = columnDateEffectivePhaseInitiale;
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
			String columnDateEffectivePhaseContinuation) {
		this.columnDateEffectivePhaseContinuation = columnDateEffectivePhaseContinuation;
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
	public int getColumnMonth() {
		return columnMonth;
	}

	public void setColumnMonth(int columnMonth) {
		this.columnMonth = columnMonth;
	}
}
