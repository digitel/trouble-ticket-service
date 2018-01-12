package org.tmf.openapi.troubleticket.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.tmf.openapi.troubleticket.domain.Status;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;

@SuppressWarnings("serial")
public class TroubleTicketSpecifications {

	public static Specification<TroubleTicket> status(final Status status) {

		return new Specification<TroubleTicket>() {

			@Override
			public Predicate toPredicate(Root<TroubleTicket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate ttStatus = cb.equal(root.get("status"), status);
				return ttStatus;
			}
		};
	}

	public static Specification<TroubleTicket> id(final String id) {

		return new Specification<TroubleTicket>() {

			@Override
			public Predicate toPredicate(Root<TroubleTicket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate ttStatus = cb.equal(root.get("id"), id);
				return ttStatus;
			}
		};
	}

}
