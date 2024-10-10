package com.xico.pruebaArkon.service;

import com.xico.pruebaArkon.dto.TicketDto;

public interface TicketService {
  public void createTickets(Long idEvent, Integer totalTickets);
  public void updateTickets(Long idEvent, Integer totalTickets);
  public TicketDto sellTicket(Long idEvent);
  public TicketDto changeTicket(Long idTicket);
}
