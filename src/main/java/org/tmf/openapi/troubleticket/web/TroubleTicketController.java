package org.tmf.openapi.troubleticket.web;

import static org.tmf.openapi.troubleticket.common.ObjectMapper.mapObjectWithExcludeFilter;
import static org.tmf.openapi.troubleticket.repository.TroubleTicketSpecificationBuilder.buildSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;
import org.tmf.openapi.troubleticket.service.TroubleTicketService;

@RestController
@RequestMapping("/api/troubleTicket")
public class TroubleTicketController {

	@Autowired
	private TroubleTicketService troubleTicketService;

	@PostMapping()
	public ResponseEntity<MappingJacksonValue> createTroubleTicket(@RequestBody TroubleTicket troubleTicket) {

		troubleTicket = troubleTicketService.createTroubleTicket(troubleTicket);
		return ResponseEntity
				.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(troubleTicket.getId()).toUri())
				.body(mapObjectWithExcludeFilter(troubleTicket, null));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<MappingJacksonValue> patchTroubleTicket(@PathVariable Long id,
			@RequestBody TroubleTicket troubleTicket) {

		validateTroubleTicket(id, troubleTicket);
		return ResponseEntity
				.ok(mapObjectWithExcludeFilter(troubleTicketService.partialUpdateTroubleTicket(troubleTicket), null));

	}

	@PutMapping("/{id}")
	public ResponseEntity<MappingJacksonValue> updateTroubleTicket(@PathVariable Long id,
			@RequestBody TroubleTicket troubleTicket) {

		validateTroubleTicket(id, troubleTicket);
		return ResponseEntity
				.ok(mapObjectWithExcludeFilter(troubleTicketService.updateTroubleTicket(troubleTicket), null));

	}

	@GetMapping("/{id}")
	public ResponseEntity<MappingJacksonValue> getTroubleTicket(@PathVariable Long id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(troubleTicketService.findTroubleTicket(id), requestParams));

	}

	// TODO Implement header pagination for GET All data operation.
	@GetMapping()
	public ResponseEntity<MappingJacksonValue> getTroubleTicket(
			@RequestParam MultiValueMap<String, String> requestParams, Pageable pageable) {

		return ResponseEntity.ok(mapObjectWithExcludeFilter(troubleTicketService
				.findTroubleTicketPageable(buildSpecification(requestParams), pageable).getContent(), requestParams));
	}

	private void validateTroubleTicket(Long id, TroubleTicket troubleTicket) {
		if ((null == troubleTicket.getId()) || (null != troubleTicket.getId() && id != troubleTicket.getId())) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
