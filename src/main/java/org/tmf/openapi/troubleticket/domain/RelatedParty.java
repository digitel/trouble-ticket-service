/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.openapi.troubleticket.domain;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class RelatedParty implements Serializable {

	private static final long serialVersionUID = 5554622384644227857L;

	@NotNull(message = "href is Mandatory")
	private URI href;
	private String role;
	private String name;
	private String validFor; // TODO confirm type.

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValidFor() {
		return validFor;
	}

	public void setValidFor(String validFor) {
		this.validFor = validFor;
	}
	
	

}
