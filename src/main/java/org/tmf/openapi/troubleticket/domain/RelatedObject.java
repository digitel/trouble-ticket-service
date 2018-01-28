package org.tmf.openapi.troubleticket.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class RelatedObject implements Serializable {

	private static final long serialVersionUID = -214534918692468556L;

	@NotEmpty(message = "Reference is Mandatory")
	private String reference;
	private String involvement;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getInvolvement() {
		return involvement;
	}

	public void setInvolvement(String involvement) {
		this.involvement = involvement;
	}

}
