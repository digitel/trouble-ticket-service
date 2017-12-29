/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.openapi.troubleticket.domain;

public enum Status {

	SUBMITTED, ACKNOWLEDGED, IN_PROGRESS, IN_PROGRESS_HELD, IN_PROGRESS_PENDING, RESOLVED, CLOSED, REJECTED, CANCELLED;

	/**
	 *
	 * @param text
	 * @return
	 */
	public static Status fromString(String text) {
		if (text != null) {
			for (Status b : Status.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					return b;
				}
			}
		}
		return null;
	}
}
