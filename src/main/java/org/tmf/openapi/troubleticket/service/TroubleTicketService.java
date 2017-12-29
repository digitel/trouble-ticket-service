package org.tmf.openapi.troubleticket.service;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.troubleticket.domain.Status;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;
import org.tmf.openapi.troubleticket.repository.TroubleTicketRepository;

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

			throw new IllegalArgumentException("id, correlationId and creationDate cannot be updated.");
		}

		Optional<TroubleTicket> existingTroubleTicketOption = troubleTicketRepository.findById(troubleTicket.getId());
		if (!existingTroubleTicketOption.isPresent()) {
			throw new IllegalArgumentException("Trouble Ticket with id " + troubleTicket.getId() + " doesnot exists");
		}

		TroubleTicket existingTroubleTicket = existingTroubleTicketOption.get();
		existingTroubleTicket.setDescription(troubleTicket.getDescription());
		existingTroubleTicket.setSeverity(troubleTicket.getSeverity());
		existingTroubleTicket.setType(troubleTicket.getType());
		existingTroubleTicket.setTargetResolutionDate(troubleTicket.getTargetResolutionDate());
		existingTroubleTicket.setStatus(troubleTicket.getStatus());
		existingTroubleTicket.setSubStatus(troubleTicket.getSubStatus());
		existingTroubleTicket.setStatusChangeReason(troubleTicket.getStatusChangeReason());
		existingTroubleTicket.setStatusChangeDate(troubleTicket.getStatusChangeDate());
		existingTroubleTicket.setResolutionDate(troubleTicket.getResolutionDate());

		// TODO change element collection to one to many and implement correct patching
		// on
		existingTroubleTicket.setRelatedParties(troubleTicket.getRelatedParties());
		existingTroubleTicket.setRelatedObjects(troubleTicket.getRelatedObjects());
		existingTroubleTicket.setNotes(troubleTicket.getNotes());

		return troubleTicketRepository.save(troubleTicket);
	}

	public TroubleTicket fullUpdateTroubleTicket(@Valid TroubleTicket troubleTicket) {

		return troubleTicketRepository.save(troubleTicket);
	}

}
