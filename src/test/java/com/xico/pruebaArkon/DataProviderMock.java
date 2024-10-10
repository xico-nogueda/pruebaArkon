package com.xico.pruebaArkon;

import com.xico.pruebaArkon.entity.Event;
import com.xico.pruebaArkon.entity.Ticket;

import java.util.List;
import java.time.LocalDate;

public class DataProviderMock {

  public static Event newEventMock(){
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusDays(30);
    return Event.builder().name("newEventoMock").startDate(startDate).endDate(endDate).totalTicket(200).build();
  }

  public static Event eventMock(){
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusDays(30);
    return Event.builder().id(10L).name("Event Mock").startDate(startDate).endDate(endDate).totalTicket(200).ticketsSold(100).build();
  }

  public static List<Ticket> listNewTicketsMocks(){
    Event event = Event.builder().id(1L).build();
    return List.of(
      new Ticket(1L, false, false, event),
      new Ticket(2L, false,false, event),
      new Ticket(3L, false,false, event),
      new Ticket(4L, false,false, event),
      new Ticket(5L, false,false, event)
    );
  }

  public static Ticket ticketSoldMock(){
    Event event = Event.builder().id(1L).build();
    return new Ticket(1L, false, true, event);
  }

  public static Ticket ticketToChangeMock(){
    Event event = Event.builder().id(1L).build();
    return new Ticket(1L, false, true, event);
  }

  public static Ticket ticketChangedMock(){
    Event event = Event.builder().id(1L).build();
    return new Ticket(1L, true, true, event);
  }
}
