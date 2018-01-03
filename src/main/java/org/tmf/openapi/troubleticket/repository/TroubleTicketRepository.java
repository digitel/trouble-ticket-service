package org.tmf.openapi.troubleticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.tmf.openapi.troubleticket.domain.TroubleTicket;

@Repository
public interface TroubleTicketRepository
		extends JpaRepository<TroubleTicket, Long>, JpaSpecificationExecutor<TroubleTicket> {

}
