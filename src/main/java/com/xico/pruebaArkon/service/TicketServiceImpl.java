package com.xico.pruebaArkon.service;

import java.util.Optional;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.xico.pruebaArkon.dto.TicketDto;
import com.xico.pruebaArkon.entity.Event;
import com.xico.pruebaArkon.entity.Ticket;
import com.xico.pruebaArkon.repository.EventRepository;
import com.xico.pruebaArkon.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketRepository ticketRepository;
  private final EventRepository eventRepository;

  @Override
  public TicketDto sellTicket(Long idEvent) {
    Optional<Event> eventOp = eventRepository.findById(idEvent);
    if (!eventOp.isPresent()) {
      throw new IllegalArgumentException("El evento no Existe");
    }
    
    Event event = eventOp.get();
    if (!(event.getTotalTicket() > event.getTicketsSold())) {
      throw new IllegalArgumentException("No existen boletos disponibles para el Evento elegido");
    }
    Ticket ticketToSell = Ticket.builder().changed(false).event(event).build();
    Ticket ticketSold = ticketRepository.save(ticketToSell);

    return TicketDto.builder()
        .id(ticketSold.getId())
        .idEvent(ticketSold.getEvent().getId())
        .build();

  }

  @Override
  public TicketDto changeTicket(Long idTicket) {
    Optional<Ticket> ticketOp = ticketRepository.findById(idTicket);
    if(!ticketOp.isPresent()){
      throw new IllegalArgumentException("El boleto no Existe");
    }

    Ticket ticketToChange = ticketOp.get();
    Event event = ticketToChange.getEvent();
    LocalDate now = LocalDate.now();
    if(now.isBefore(event.getStartDate()) || now.isAfter(event.getEndDate())){
      throw new IllegalArgumentException("Un boleto solo puede ser canjeado 1 vez y únicamente dentro de los limites de inicio y fin del evento al que pertenece");
    }

    ticketToChange.setChanged(true);
    Ticket tiketChanged = ticketRepository.save(ticketToChange);

    return TicketDto.builder()
        .id(tiketChanged.getId())
        .ticketChanged(tiketChanged.isChanged())
        .idEvent(tiketChanged.getEvent().getId())
        .build();
  }

}
