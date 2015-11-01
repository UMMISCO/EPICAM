package org.imogene.epicam.server.birtPojo;

public class StockMedicaments {


	private String columnNomCDT;
	private String columnNomPersonnel;
	
	private String columnDesignation;
	private int columnStockActuel;
	private int columnQuantiteDemande;
	private int columnQuantiteFournie;
	public StockMedicaments() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StockMedicaments(String columnDesignation, int columnStockActuel,
			int columnQuantiteDemande, int columnQuantiteFournie) {
		super();
		this.columnDesignation = columnDesignation;
		this.columnStockActuel = columnStockActuel;
		this.columnQuantiteDemande = columnQuantiteDemande;
		this.columnQuantiteFournie = columnQuantiteFournie;
	}
	public String getColumnDesignation() {
		return columnDesignation;
	}
	public void setColumnDesignation(String columnDesignation) {
		this.columnDesignation = columnDesignation;
	}
	public int getColumnStockActuel() {
		return columnStockActuel;
	}
	public void setColumnStockActuel(int columnStockActuel) {
		this.columnStockActuel = columnStockActuel;
	}
	public int getColumnQuantiteDemande() {
		return columnQuantiteDemande;
	}
	public void setColumnQuantiteDemande(int columnQuantiteDemande) {
		this.columnQuantiteDemande = columnQuantiteDemande;
	}
	public int getColumnQuantiteFournie() {
		return columnQuantiteFournie;
	}
	public void setColumnQuantiteFournie(int columnQuantiteFournie) {
		this.columnQuantiteFournie = columnQuantiteFournie;
	}
	public String getColumnNomCDT() {
		return columnNomCDT;
	}
	public void setColumnNomCDT(String columnNomCDT) {
		this.columnNomCDT = columnNomCDT;
	}
	public String getColumnNomPersonnel() {
		return columnNomPersonnel;
	}
	public void setColumnNomPersonnel(String columnNomPersonnel) {
		this.columnNomPersonnel = columnNomPersonnel;
	}
	
}
