package org.tmf.openapi.troubleticket.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class Note implements Serializable {

	private static final long serialVersionUID = -2914865680400773797L;

	private String date;
	//TODO change String to Date.
	
	@NotEmpty(message = "Author is Mandatory")
	private String author;

	@NotEmpty(message = "Note text is Mandatory")
	private String text;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
