package org.imogene.epicam.server.birtPojo;

public class ExamenBiologique {
	

	private String columnDate;
	private String columnPoids;
	
	public ExamenBiologique() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamenBiologique(String columnDate, String columnPoids) {
		super();
		this.columnDate = columnDate;
		this.columnPoids = columnPoids;
	}

	public String getColumnDate() {
		return columnDate;
	}

	public void setColumnDate(String columnDate) {
		this.columnDate = columnDate;
	}

	public String getColumnPoids() {
		return columnPoids;
	}

	public void setColumnPoids(String columnPoids) {
		this.columnPoids = columnPoids;
	}
	
	

}
