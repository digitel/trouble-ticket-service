/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.openapi.troubleticket.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RelatedParty implements Serializable {

	private static final long serialVersionUID = 5554622384644227857L;

	private String role;
	private String reference;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
