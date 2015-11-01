package org.imogene.epicam.server.birtPojo;

import java.util.Date;

public class TransfertReferenceForm {
	
	private String columnTypeOP;
	//Section A
	private String columnCDTDepart;
	private String columnCDTArrive;
	private String columnNumRegTBDepart;
	private String columnNumRegTBArrivee;
	private String columnNom;
	private int columnAge;
	private String columnSexe;
	private String columnFormeMaladie;
	private String columnTypeMaladie;
	//For exams
	private Date columnDateExamen;
	private String columnRaisonExamen;
	private String columnResultatExamen;
	//Treatments
	private String columnTraitementCommence;
	private Date columnDateDebutTraitement;
	private String columnTraitementPrescrit;
	
	//Reference
	private Date columnDateReference;
	private Date columnDateArriveeMalade;
	
	//Health worker who refer
	private String columnNomAgentReferent;
	private String columnNomAgentRecoit;
	
	private String columnAdresse;
		
	public TransfertReferenceForm() {
		super();
	}

	public String getColumnTypeOP() {
		return columnTypeOP;
	}

	public void setColumnTypeOP(String columnTypeOP) {
		this.columnTypeOP = columnTypeOP;
	}

	public String getColumnCDTDepart() {
		return columnCDTDepart;
	}

	public void setColumnCDTDepart(String columnCDTDepart) {
		this.columnCDTDepart = columnCDTDepart;
	}

	public String getColumnAdresse() {
		return columnAdresse;
	}

	public void setColumnAdresse(String columnAdresse) {
		this.columnAdresse = columnAdresse;
	}

	public String getColumnCDTArrive() {
		return columnCDTArrive;
	}

	public void setColumnCDTArrive(String columnCDTArrive) {
		this.columnCDTArrive = columnCDTArrive;
	}

	public String getColumnNumRegTBDepart() {
		return columnNumRegTBDepart;
	}

	public void setColumnNumRegTBDepart(String columnNumRegTBDepart) {
		this.columnNumRegTBDepart = columnNumRegTBDepart;
	}

	public String getColumnNumRegTBArrivee() {
		return columnNumRegTBArrivee;
	}

	public void setColumnNumRegTBArrivee(String columnNumRegTBArrivee) {
		this.columnNumRegTBArrivee = columnNumRegTBArrivee;
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

	public String getColumnSexe() {
		return columnSexe;
	}

	public void setColumnSexe(String columnSexe) {
		this.columnSexe = columnSexe;
	}

	public String getColumnFormeMaladie() {
		return columnFormeMaladie;
	}

	public void setColumnFormeMaladie(String columFormeMaladie) {
		this.columnFormeMaladie = columFormeMaladie;
	}

	public String getColumnTypeMaladie() {
		return columnTypeMaladie;
	}

	public void setColumnTypeMaladie(String columnTypeMaladie) {
		this.columnTypeMaladie = columnTypeMaladie;
	}

	public Date getColumnDateExamen() {
		return columnDateExamen;
	}

	public void setColumnDateExamen(Date columnDateExamen) {
		this.columnDateExamen = columnDateExamen;
	}

	public String getColumnRaisonExamen() {
		return columnRaisonExamen;
	}

	public void setColumnRaisonExamen(String columnRaisonExamen) {
		this.columnRaisonExamen = columnRaisonExamen;
	}

	public String getColumnResultatExamen() {
		return columnResultatExamen;
	}

	public void setColumnResultatExamen(String columnResultatExamen) {
		this.columnResultatExamen = columnResultatExamen;
	}

	public String getColumnTraitementCommence() {
		return columnTraitementCommence;
	}

	public void setColumnTraitementCommence(String columnTraitementCommence) {
		this.columnTraitementCommence = columnTraitementCommence;
	}

	public Date getColumnDateDebutTraitement() {
		return columnDateDebutTraitement;
	}

	public void setColumnDateDebutTraitement(Date columnDateDebutTraitement) {
		this.columnDateDebutTraitement = columnDateDebutTraitement;
	}

	public String getColumnTraitementPrescrit() {
		return columnTraitementPrescrit;
	}

	public void setColumnTraitementPrescrit(String columnTraitementPrescrit) {
		this.columnTraitementPrescrit = columnTraitementPrescrit;
	}

	public Date getColumnDateReference() {
		return columnDateReference;
	}

	public void setColumnDateReference(Date columnDateReference) {
		this.columnDateReference = columnDateReference;
	}

	public Date getColumnDateArriveeMalade() {
		return columnDateArriveeMalade;
	}

	public void setColumnDateArriveeMalade(Date columnDateArriveeMalade) {
		this.columnDateArriveeMalade = columnDateArriveeMalade;
	}

	public String getColumnNomAgentReferent() {
		return columnNomAgentReferent;
	}

	public void setColumnNomAgentReferent(String columnNomAgentReferent) {
		this.columnNomAgentReferent = columnNomAgentReferent;
	}

	public String getColumnNomAgentRecoit() {
		return columnNomAgentRecoit;
	}

	public void setColumnNomAgentRecoit(String columnNomAgentRecoit) {
		this.columnNomAgentRecoit = columnNomAgentRecoit;
	}


}
