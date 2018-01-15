package org.tmf.openapi.troubleticket.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.tmf.openapi.troubleticket.common.SearchCriteria;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;

public class TroubleTicketSpecificationBuilder {

	public static Specification<TroubleTicket> buildSpecification(MultiValueMap<String, String> requestParams) {

		Specification<TroubleTicket> specification = null;

		for (Entry<String, List<String>> entry : requestParams.entrySet()) {

			if (entry.getKey().toLowerCase().contains("range")) {
				continue;
			}

			SearchCriteria criteria = determineSearchCriteria(entry);

			if (null != entry.getKey() && entry.getKey().contains("id")) {

				Specification<TroubleTicket> idSpecification = null;

				for (String id : criteria.getValues()) {

					idSpecification = (null == idSpecification
							? TroubleTicketSpecifications.id(id, criteria.getOperator())
							: idSpecification.or(TroubleTicketSpecifications.id(id, criteria.getOperator())));
				}

				specification = (null == specification ? idSpecification : specification.and(idSpecification));
			} else if (null != entry.getKey() && entry.getKey().contains("correlationId")) {

				Specification<TroubleTicket> correlationIdSpecification = null;

				for (String correlationId : criteria.getValues()) {

					correlationIdSpecification = (null == correlationIdSpecification
							? TroubleTicketSpecifications.correlationId(correlationId, criteria.getOperator())
							: correlationIdSpecification.or(
									TroubleTicketSpecifications.correlationId(correlationId, criteria.getOperator())));
				}

				specification = (null == specification ? correlationIdSpecification
						: specification.and(correlationIdSpecification));
			}

		}

		return specification;

	}

	// TODO Identify better way to clean below shit.

	private static SearchCriteria determineSearchCriteria(Entry<String, List<String>> entry) {

		SearchCriteria criteria = new SearchCriteria();

		if (entry.getKey().contains(".")) {

			criteria.setKey(entry.getKey().substring(0, entry.getKey().indexOf(".")));
			criteria.setOperator(Operator.fromString(entry.getKey().substring(entry.getKey().indexOf(".") + 1)));

			List<String> values = entry.getValue();
			List<String> valueList = new ArrayList<>();
			values.forEach(item -> {
				System.out.println(item);
				valueList.addAll(Arrays.asList(item.split(",")));
			});

			criteria.getValues().addAll(valueList);

		}

		else if (entry.getKey().contains("<") || entry.getKey().contains(">") || entry.getKey().contains("*")) {

			String operator = null;

			if (entry.getKey().contains("<")) {

				if (entry.getValue().get(0).isEmpty()) {
					operator = "<";
					criteria.setOperator(Operator.LT);
					criteria.getValues().add(entry.getKey().substring(entry.getKey().indexOf(operator) + 1));
				} else {
					operator = "<=";
					criteria.setOperator(Operator.LTE);

					criteria.getValues().add(entry.getValue().get(0));
				}

			} else if (entry.getKey().contains(">")) {

				if (entry.getValue().get(0).isEmpty()) {
					operator = ">";
					criteria.setOperator(Operator.GT);
					criteria.getValues().add(entry.getKey().substring(entry.getKey().indexOf(operator) + 1));
				} else {
					operator = ">=";
					criteria.setOperator(Operator.GTE);
					criteria.getValues().add(entry.getValue().get(0));
				}

			}

			else if (entry.getKey().contains("*")) {

				operator = "*";
				criteria.setOperator(Operator.LIKE);
				criteria.getValues().add(entry.getValue().get(0));

			}

		} else {

			criteria.setKey(entry.getKey());
			criteria.setOperator(Operator.EQ);

			List<String> values = entry.getValue();
			List<String> valueList = new ArrayList<>();
			values.forEach(item -> {
				System.out.println(item);
				valueList.addAll(Arrays.asList(item.split(",")));
			});

			criteria.getValues().addAll(valueList);

		}

		return criteria;
	}

	public enum Operator {

		EQ("eq"), GT("gt"), GTE("gte"), LT("lt"), LTE("lte"), NE("ne"), EX("ex"), LIKE("like");

		private String value;

		Operator(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public static Operator fromString(String value) {
			if (value != null) {
				for (Operator b : Operator.values()) {
					if (value.equalsIgnoreCase(b.value)) {
						return b;
					}
				}
			}
			return null;
		}
	}

}
