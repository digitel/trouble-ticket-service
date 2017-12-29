package org.tmf.openapi.troubleticket.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Report {

	private String previousRows;
	private String currentRows;
	private String affectedRows;


	public Report() {
	}

	public Report(int currentRows) {
		this.currentRows = String.valueOf(currentRows);
	}

	public String getCurrentRows() {
		return currentRows;
	}

	public void setCurrentRows(int currentRows) {
		this.currentRows = String.valueOf(currentRows);
	}

	public String getPreviousRows() {
		return previousRows;
	}

	public void setPreviousRows(int previousRows) {
		this.previousRows = String.valueOf(previousRows);
	}

	public String getAffectedRows() {
		return affectedRows;
	}

	public void setAffectedRows(int affectedRows) {
		this.affectedRows = String.valueOf(affectedRows);
	}

}