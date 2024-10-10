package com.xico.pruebaArkon;

import com.xico.pruebaArkon.entity.Event;

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
    return Event.builder().id(10L).name("Evento Mock").startDate(startDate).endDate(endDate).totalTicket(200).build();
  }
}
