package org.tmf.openapi.troubleticket.common;

import java.util.ArrayList;
import java.util.List;

import org.tmf.openapi.troubleticket.repository.TroubleTicketSpecificationBuilder.Operator;

public class SearchCriteria {

	private String key;
	private Operator operator;
	private List<String> values;

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public List<String> getValues() {
		if (null == values) {
			values = new ArrayList<>();
		}
		return values;
	}

}
