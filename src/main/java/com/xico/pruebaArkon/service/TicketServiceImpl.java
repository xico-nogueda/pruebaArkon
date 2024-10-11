package com.xico.pruebaArkon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.xico.pruebaArkon.dto.TicketDto;
import com.xico.pruebaArkon.entity.Event;
import com.xico.pruebaArkon.entity.Ticket;
import com.xico.pruebaArkon.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketRepository ticketRepository;

  @Override
  public void createTickets(Long idEvent, Integer totalTickets) {
    Event event = Event.builder().id(idEvent).build();
    List<Ticket> tickets = new ArrayList<>();
    for (int i = 0; i < totalTickets; i++) {
      Ticket ticket = Ticket.builder().changed(false).sold(false).event(event).build();
      tickets.add(ticket);
    }
    ticketRepository.saveAll(tickets);
  }

  @Override
  public void updateTickets(Long idEvent, Integer totalTickets) {
    List<Ticket> tickets = ticketRepository.findByEventId(idEvent);
    List<Ticket> ticketsSold = tickets.stream().filter(ticket -> ticket.isSold()).toList();
    List<Ticket> ticketsNotSold = tickets.stream().filter(ticket -> !ticket.isSold()).toList();
    int difference = totalTickets - ticketsSold.size();

    if (tickets.size() > totalTickets) {
      for (int i = 1; i <= difference; i++) {
        Ticket ticket = ticketsNotSold.get(ticketsNotSold.size() - i);
        ticketRepository.delete(ticket);
      }
    } else {
      Event event = Event.builder().id(idEvent).build();
      List<Ticket> ticketsNew = new ArrayList<>();
      for (int i = 1; i <= difference; i++) {
        Ticket ticket = Ticket.builder().changed(false).sold(false).event(event).build();
        ticketsNew.add(ticket);
      }
      ticketRepository.saveAll(ticketsNew);
    }
  }

  @Override
  public TicketDto sellTicket(Long idEvent) {
    List<Ticket> tickets = ticketRepository.findByEventId(idEvent);
    if (tickets.isEmpty()) {
      throw new IllegalArgumentException("No existen boletos para el Evento");
    }
    List<Ticket> ticketsNotSold = tickets.stream().filter(ticket -> !ticket.isSold()).toList();
    if (ticketsNotSold.isEmpty()) {
      throw new IllegalArgumentException("No existen boletos disponibles para el Evento elegido");
    }
    Ticket ticketToSell = ticketsNotSold.get(0);
    ticketToSell.setSold(true);
    Ticket ticketSold = ticketRepository.save(ticketToSell);

    return TicketDto.builder()
        .id(ticketSold.getId())
        .ticketChanged(ticketSold.isChanged())
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
