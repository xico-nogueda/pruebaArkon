package com.xico.pruebaArkon.service;

import com.xico.pruebaArkon.dto.TicketDto;

public interface TicketService {
  public TicketDto sellTicket(Long idEvent);
  public TicketDto changeTicket(Long idTicket);
}
