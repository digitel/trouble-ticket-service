/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.openapi.troubleticket.domain;

public enum Severity {

	LOW, CRITICAL, MEDIUM, HIGH;

	/**
	 *
	 * @param text
	 * @return
	 */
	public static Severity fromString(String text) {
		if (text != null) {
			for (Severity b : Severity.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					return b;
				}
			}
		}
		return null;
	}
}
