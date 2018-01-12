package org.tmf.openapi.troubleticket.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ObjectMapper {

	public static MappingJacksonValue mapObjectWithExcludeFilter(Object object,
			MultiValueMap<String, String> requestParams) {

		Set<String> fields = new HashSet<>();

		if (null != requestParams && requestParams.size() > 0) {

			// Handling for URI Like -> /api/resource/1?fields=field1,field2
			if (requestParams.containsKey("fields")) {
				List<String> fieldList = requestParams.get("fields");
				for (String field : fieldList) {
					fields.addAll(Arrays.asList(field.split(",")));
				}
				fields.add("id");
			}

			// Handling for URI Like -> /api/resource/1?field1,field2
			for (Entry<String, List<String>> entry : requestParams.entrySet()) {
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
