package org.tmf.openapi.troubleticket.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.troubleticket.domain.Status;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;
import org.tmf.openapi.troubleticket.repository.TroubleTicketRepository;
import org.tmf.openapi.troubleticket.repository.TroubleTicketSpecifications;

@Service
public class TroubleTicketService {

	@Autowired
	private TroubleTicketRepository troubleTicketRepository;

	public TroubleTicket createTroubleTicket(@Valid TroubleTicket troubleTicket) {

		if (troubleTicket.getId() != null || troubleTicket.getCreationDate() != null
				|| troubleTicket.getStatus() != null) {

			throw new IllegalArgumentException(
					"id, creationDate and status must be empty while creating Trouble Ticket.");
		}

		Date now = new Date();
		troubleTicket.setCreationDate(now);
		troubleTicket.setStatusChangeDate(now);
		troubleTicket.setStatus(Status.SUBMITTED);
		return troubleTicketRepository.save(troubleTicket);
	}

	public TroubleTicket partialUpdateTroubleTicket(TroubleTicket troubleTicket) {

		if (null == troubleTicket.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		if (troubleTicket.getCorrelationId() != null || troubleTicket.getCreationDate() != null) {

			throw new IllegalArgumentException("correlationId and creationDate cannot be updated.");
		}

		Optional<TroubleTicket> existingTroubleTicketOption = troubleTicketRepository.findById(troubleTicket.getId());
		if (!existingTroubleTicketOption.isPresent()) {
			throw new IllegalArgumentException("Trouble Ticket with id " + troubleTicket.getId() + " doesnot exists");
		}

		TroubleTicket existingTroubleTicket = existingTroubleTicketOption.get();

		if (null != troubleTicket.getDescription()) {
			existingTroubleTicket.setDescription(troubleTicket.getDescription());
		}

		if (null != troubleTicket.getSeverity()) {
			existingTroubleTicket.setSeverity(troubleTicket.getSeverity());
		}

		if (null != troubleTicket.getType()) {
			existingTroubleTicket.setType(troubleTicket.getType());
		}

		if (null != troubleTicket.getTargetResolutionDate()) {
			existingTroubleTicket.setTargetResolutionDate(troubleTicket.getTargetResolutionDate());
		}

		if (null != troubleTicket.getStatus()) {
			if (existingTroubleTicket.getStatus() == Status.RESOLVED && troubleTicket.getStatus() != Status.CLOSED) {

				throw new IllegalArgumentException(
						"Trouble Ticket with Status " + Status.RESOLVED + " can only be " + Status.CLOSED);
			}

			if ((existingTroubleTicket.getStatus() == Status.SUBMITTED
					|| existingTroubleTicket.getStatus() == Status.ACKNOWLEDGED
					|| existingTroubleTicket.getStatus() == Status.IN_PROGRESS)
					&& troubleTicket.getStatus() != Status.CANCELLED) {

				throw new IllegalArgumentException("Trouble Ticket with Status " + Status.SUBMITTED + ","
						+ Status.ACKNOWLEDGED + "," + Status.IN_PROGRESS + " can only be " + Status.CANCELLED);
			}
			existingTroubleTicket.setStatus(troubleTicket.getStatus());
		}

		if (null != troubleTicket.getSubStatus()) {
			existingTroubleTicket.setSubStatus(troubleTicket.getSubStatus());
		}

		if (null != troubleTicket.getStatusChangeReason()) {
			existingTroubleTicket.setStatusChangeReason(troubleTicket.getStatusChangeReason());
		}

		if (null != troubleTicket.getStatusChangeDate()) {
			existingTroubleTicket.setStatusChangeDate(troubleTicket.getStatusChangeDate());
		}

		if (null != troubleTicket.getResolutionDate()) {
			existingTroubleTicket.setResolutionDate(troubleTicket.getResolutionDate());
		}

		if (null != troubleTicket.getRelatedParties()) {
			existingTroubleTicket.setRelatedParties(troubleTicket.getRelatedParties());
		}

		if (null != troubleTicket.getRelatedObjects()) {
			existingTroubleTicket.setRelatedObjects(troubleTicket.getRelatedObjects());
		}
		if (null != troubleTicket.getNotes()) {
			existingTroubleTicket.setNotes(troubleTicket.getNotes());
		}

		return troubleTicketRepository.save(existingTroubleTicket);
	}

	public TroubleTicket updateTroubleTicket(@Valid TroubleTicket troubleTicket) {

		return troubleTicketRepository.save(troubleTicket);
	}

	public List<TroubleTicket> findAllTroubleTicket() {
		return troubleTicketRepository.findAll();
	}
	// TODO combine these 2 ?

	public List<TroubleTicket> findTroubleTicket(TroubleTicket criteria) {

		List<TroubleTicket> tickets = new ArrayList<>();

		if (null != criteria.getId()) {

			Optional<TroubleTicket> ticket = troubleTicketRepository.findById(criteria.getId());
			if (ticket.isPresent()) {

				tickets.add(ticket.get());
				return tickets;
			}
			throw new NoSuchElementException("Trouble Ticket with id " + criteria.getId() + " doesnot exists");
		}

		if (null != criteria.getStatus()) {
			List<TroubleTicket> result = troubleTicketRepository
					.findAll(TroubleTicketSpecifications.status(criteria.getStatus()));
			return result;
		}

		return troubleTicketRepository.findAll();
	}

}
