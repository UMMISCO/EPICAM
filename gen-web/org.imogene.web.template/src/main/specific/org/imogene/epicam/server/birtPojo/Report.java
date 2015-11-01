package org.imogene.epicam.server.birtPojo;


public class Report {
	

	private static final String RAPPORT_TRIMESTRE_TRIMESTRE1 = "trimestre1";
	private static final String RAPPORT_TRIMESTRE_TRIMESTRE2 = "trimestre2";
	private static final String RAPPORT_TRIMESTRE_TRIMESTRE3 = "trimestre3";
	private static final String RAPPORT_TRIMESTRE_TRIMESTRE4 = "trimestre4";
	
//	parameters for type of reports

	private static final String REGISTRETB = "RegTB";
	private static final String REGISTRETBENFANT = "RegTBEfants";
	private static final String REGISTRELABO = "RegLabo";
	private static final String RAPPORTTRIMDESPIST = "RapTrimDepist";
	private static final String RAPPORTTRIMTRAIT = "RapTrimTraitement";
	private static final String RAPPORTTRIMLABO = "RapTrimLabo";
	private static final String CARTEPAT = "CartePatient";
	private static final String FICHETRAIT = "FicheTraitement";
	private static final String FICHETRANSREF = "FicheTransRef"; 
	private static final String RAPDISTRIBMEDIC = "RapDistribMedicament";	
	
	private String nom;
	private int version;

	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Report(String nom) {
		super();
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	

}
