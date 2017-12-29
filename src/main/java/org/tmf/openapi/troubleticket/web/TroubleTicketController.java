package org.tmf.openapi.troubleticket.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;
import org.tmf.openapi.troubleticket.service.TroubleTicketService;

@RestController
@RequestMapping("/api/troubleTicket")
public class TroubleTicketController {

	@Autowired
	private TroubleTicketService troubleTicketService;

	@PostMapping()
	public ResponseEntity<Object> createTroubleTicket(@RequestBody TroubleTicket troubleTicket) {

		troubleTicket = troubleTicketService.createTroubleTicket(troubleTicket);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(troubleTicket.getId()).toUri();
		return ResponseEntity.created(location).body(troubleTicket);

	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> patchTroubleTicket(@PathVariable Long id, @RequestBody TroubleTicket troubleTicket) {

		if (null != troubleTicket.getId()) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
		troubleTicket.setId(id);
		troubleTicket = troubleTicketService.partialUpdateTroubleTicket(troubleTicket);
		return ResponseEntity.ok(troubleTicket);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateTroubleTicket(@PathVariable Long id, @RequestBody TroubleTicket troubleTicket) {

		if (null != troubleTicket.getId() && id != troubleTicket.getId()) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
		troubleTicket = troubleTicketService.fullUpdateTroubleTicket(troubleTicket);
		return ResponseEntity.ok(troubleTicket);

	}

}
