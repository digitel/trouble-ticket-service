package org.tmf.openapi.troubleticket.web;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
import org.tmf.openapi.troubleticket.domain.Status;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;
import org.tmf.openapi.troubleticket.service.TroubleTicketService;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/api/troubleTicket")
public class TroubleTicketController {

	@Autowired
	private TroubleTicketService troubleTicketService;

	@PostMapping()
	public ResponseEntity<MappingJacksonValue> createTroubleTicket(@RequestBody TroubleTicket troubleTicket) {

		troubleTicket = troubleTicketService.createTroubleTicket(troubleTicket);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(troubleTicket.getId()).toUri();
		return ResponseEntity.created(location).body(mapObjectWithExcludeFilter(troubleTicket, null));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<MappingJacksonValue> patchTroubleTicket(@PathVariable Long id,
			@RequestBody TroubleTicket troubleTicket) {

		if (null != troubleTicket.getId()) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
		troubleTicket.setId(id);
		troubleTicket = troubleTicketService.partialUpdateTroubleTicket(troubleTicket);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(troubleTicket, null));

	}

	@PutMapping("/{id}")
	public ResponseEntity<MappingJacksonValue> updateTroubleTicket(@PathVariable Long id,
			@RequestBody TroubleTicket troubleTicket) {

		if ((null == troubleTicket.getId()) || (null != troubleTicket.getId() && id != troubleTicket.getId())) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
		troubleTicket = troubleTicketService.updateTroubleTicket(troubleTicket);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(troubleTicket, null));

	}

	@GetMapping("/{id}")
	public ResponseEntity<MappingJacksonValue> getTroubleTicket(@PathVariable Long id,
			@RequestParam Map<String, String> allRequestParams) {

		TroubleTicket criteria = new TroubleTicket();
		criteria.setId(id);
		TroubleTicket ticket = troubleTicketService.findTroubleTicket(criteria).get(0);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(ticket, allRequestParams));

	}

	// TODO introduce header pagination. as data will be huge.
	@GetMapping()
	public ResponseEntity<MappingJacksonValue> getTroubleTicket(@RequestParam Map<String, String> allRequestParams) {

		if (0 == allRequestParams.size()) {
			return ResponseEntity.ok(mapObjectWithExcludeFilter(troubleTicketService.findAllTroubleTicket(), null));
		}

		TroubleTicket searchCriteria = getSearchCriteria(allRequestParams);
		List<TroubleTicket> troubleTickets = troubleTicketService.findTroubleTicket(searchCriteria);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(troubleTickets, allRequestParams));
	}

	private TroubleTicket getSearchCriteria(Map<String, String> allRequestParams) {
		TroubleTicket spec = new TroubleTicket();

		for (Map.Entry<String, String> entry : allRequestParams.entrySet()) {
			System.out.println(entry.getKey() + "/" + entry.getValue());
			if (entry.getKey().equalsIgnoreCase("id")) {

				spec.setId(Long.parseLong(entry.getValue()));
			}

			if (entry.getKey().equalsIgnoreCase("status")) {

				spec.setStatus(Status.fromString(entry.getValue()));
			}
		}

		return spec;
	}

	private MappingJacksonValue mapObjectWithExcludeFilter(Object object, Map<String, String> allRequestParams) {

		Set<String> fields = new HashSet<>();

		if (null != allRequestParams && allRequestParams.size() > 0) {

			// Handling for URI Like -> /api/resource/1?fields=field1,field2
			if (allRequestParams.containsKey("fields")) {
				fields.addAll(Arrays.asList(allRequestParams.get("fields").split(",")));
				fields.add("id");
			}

			// Handling for URI Like ->  /api/resource/1?field1,field2
			for (Map.Entry<String, String> entry : allRequestParams.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
				if ((null == entry.getValue() || entry.getValue().isEmpty()) && (null != entry.getKey())) {
					fields.addAll(Arrays.asList(entry.getKey().split(",")));
					fields.add("id");
					break;
				}
			}

		}

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(object);
		SimpleFilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false);
		if (null != fields && fields.size() > 0) {
			filters.addFilter("troubleTicketFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));

		}
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}

}
