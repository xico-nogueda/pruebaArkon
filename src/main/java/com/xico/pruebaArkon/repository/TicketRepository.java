package com.xico.pruebaArkon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.xico.pruebaArkon.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
  public List<Ticket> findByEventId(Long idEvent);
}
