package com.xico.pruebaArkon.service;

import org.springframework.stereotype.Service;

import com.xico.pruebaArkon.dto.EventDto;
import com.xico.pruebaArkon.dto.TicketDto;
import com.xico.pruebaArkon.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

  private final TicketRepository ticketRepository;

  @Override
  public void createTickets(Long idEvent, Integer totalTickets) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createTickets'");
  }

  @Override
  public void updateTickets(Long idEvent, Integer totalTickets) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateTickets'");
  }

  @Override
  public TicketDto sellTicket(Long idEvent) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'sellTicket'");
  }

  @Override
  public TicketDto changeTicket(Long idTicket) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'changeTicket'");
  }
  
}
