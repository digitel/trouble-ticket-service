package org.tmf.openapi.troubleticket.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.tmf.openapi.troubleticket.common.ObjectMapper.mapObjectWithExcludeFilter;
import static org.tmf.openapi.troubleticket.repository.TroubleTicketSpecificationBuilder.buildSpecification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;
import org.tmf.openapi.troubleticket.service.TroubleTicketService;

@RestController
@RequestMapping("/api/troubleTicket")
public class TroubleTicketController {

	@Autowired
	private TroubleTicketService troubleTicketService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createTroubleTicket(@RequestBody TroubleTicket troubleTicket) {

		troubleTicket = troubleTicketService.createTroubleTicket(troubleTicket);
		return ResponseEntity.created(populateHref(troubleTicket).getHref())
				.body(mapObjectWithExcludeFilter(troubleTicket, null));
	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchTroubleTicket(@PathVariable Long id,
			@RequestBody TroubleTicket troubleTicket) {

		validateTroubleTicket(id, troubleTicket);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(troubleTicketService.partialUpdateTroubleTicket(troubleTicket)), null));

	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateTroubleTicket(@PathVariable Long id,
			@RequestBody TroubleTicket troubleTicket) {

		validateTroubleTicket(id, troubleTicket);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(troubleTicketService.updateTroubleTicket(troubleTicket)), null));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getTroubleTicket(@PathVariable Long id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(
				mapObjectWithExcludeFilter(populateHref(troubleTicketService.findTroubleTicket(id)), requestParams));

	}

	// TODO Implement header pagination for GET All data operation.
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getTroubleTicket(
			@RequestParam MultiValueMap<String, String> requestParams, Pageable pageable) {

		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(
				troubleTicketService.findTroubleTicket(buildSpecification(requestParams), pageable).getContent()),
				requestParams));
	}

	private void validateTroubleTicket(Long id, TroubleTicket troubleTicket) {
		if ((null == troubleTicket.getId()) || (null != troubleTicket.getId() && id != troubleTicket.getId())) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

	private TroubleTicket populateHref(TroubleTicket troubleTicket) {
		troubleTicket.setHref(linkTo(TroubleTicketController.class).slash(troubleTicket.getId()).toUri());
		return troubleTicket;
	}

	private List<TroubleTicket> populateHref(List<TroubleTicket> troubleTickets) {
		for (TroubleTicket troubleTicket : troubleTickets) {
			populateHref(troubleTicket);
		}
		return troubleTickets;
	}

}
