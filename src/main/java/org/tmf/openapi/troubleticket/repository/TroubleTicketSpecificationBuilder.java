package org.tmf.openapi.troubleticket.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;

public class TroubleTicketSpecificationBuilder {

	public static Specification<TroubleTicket> buildSpecification(MultiValueMap<String, String> requestParams) {

		Specification<TroubleTicket> specification = null;
		
		for (Entry<String, List<String>> entry : requestParams.entrySet()) {

			if (null != entry.getKey() && entry.getKey().equalsIgnoreCase("id")) {

				Specification<TroubleTicket> idSpecification = null;
				List<String> values = entry.getValue();
				List<String> ids = new ArrayList<>();
				values.forEach(item -> {
					System.out.println(item);
					ids.addAll(Arrays.asList(item.split(",")));
				});
				for (String id : ids) {

					idSpecification = (null == idSpecification ? TroubleTicketSpecifications.id(id)
							: idSpecification.or(TroubleTicketSpecifications.id(id)));
				}

				specification = (null == specification ? idSpecification : specification.and(idSpecification));
			}

		}

		return specification;

	}

}
