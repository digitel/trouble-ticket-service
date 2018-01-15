package org.tmf.openapi.troubleticket.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.Specification;
import org.tmf.openapi.troubleticket.domain.Status;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;
import org.tmf.openapi.troubleticket.repository.TroubleTicketSpecificationBuilder.Operator;

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

	public static Specification<TroubleTicket> id(@NotNull final String id, @NotNull final Operator operator) {

		return new Specification<TroubleTicket>() {

			@Override
			public Predicate toPredicate(Root<TroubleTicket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				switch (operator) {

				case EQ:
					return cb.equal(root.get("id"), id);
				case GT:
					return cb.greaterThan(root.get("id"), id);
				case GTE:
					return cb.greaterThanOrEqualTo(root.get("id"), id);
				case LT:
					return cb.lessThan(root.get("id"), id);
				case LTE:
					return cb.lessThanOrEqualTo(root.get("id"), id);
				case EX:
					return cb.notEqual(root.get("id"), id);
				default:
					return cb.equal(root.get("id"), id);
				}

			}
		};
	}

	public static Specification<TroubleTicket> correlationId(@NotNull final String correlationId,
			@NotNull final Operator operator) {

		return new Specification<TroubleTicket>() {

			@Override
			public Predicate toPredicate(Root<TroubleTicket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				switch (operator) {

				case EQ:
					return cb.equal(root.get("correlationId"), correlationId);
				case EX:
					return cb.notEqual(root.get("correlationId"), correlationId);
				default:
					return cb.equal(root.get("correlationId"), correlationId);
				}

			}
		};
	}

}
