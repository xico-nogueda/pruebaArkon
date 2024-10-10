package com.xico.pruebaArkon.service;

import com.xico.pruebaArkon.dto.EventDto;
import com.xico.pruebaArkon.entity.Event;
import com.xico.pruebaArkon.repository.EventRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;

  @Override
  public EventDto getEventById(Long id) {
    Optional<Event> eventOp = eventRepository.findById(id);
    if (eventOp.isEmpty()) {
      throw new IllegalArgumentException("El evento no Existe");
    }
    Event event = eventOp.get();
    EventDto eventDto = EventDto.builder()
        .id(event.getId())
        .name(event.getName())
        .startDate(event.getStartDate())
        .endDate(event.getEndDate())
        .totalTicket(event.getTotalTicket())
        .ticketsSold(event.getTicketsSold())
        .build();
    return eventDto;
  }

  @Override
  public EventDto createEvent(EventDto eventDto) {
    if (eventDto.getStartDate().isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("La fecha de Inicio no puede ser anterior a la fecha actual");
    }
    if (eventDto.getEndDate().isBefore(eventDto.getStartDate())) {
      throw new IllegalArgumentException("La fecha final no puede ser anterior a la fecha inicial");
    }
    if (eventDto.getTotalTicket() < 1 || eventDto.getTotalTicket() > 300) {
      throw new IllegalArgumentException("El total de boletos debe ser entre 1 y 300");
    }
    Optional<Event> eventExist = eventRepository.findByName(eventDto.getName());
    if (eventExist.isPresent()) {
      throw new IllegalArgumentException("Ya existe un evento con ese nombre");
    }
    Event eventNew = Event.builder()
        .name(eventDto.getName())
        .startDate(eventDto.getStartDate())
        .endDate(eventDto.getEndDate())
        .totalTicket(eventDto.getTotalTicket())
        .ticketsSold(0)
        .build();

    Event eventSaved = eventRepository.save(eventNew);

    return EventDto.builder()
        .id(eventSaved.getId())
        .name(eventSaved.getName())
        .startDate(eventSaved.getStartDate())
        .endDate(eventSaved.getEndDate())
        .totalTicket(eventSaved.getTotalTicket())
        .ticketsSold(eventSaved.getTicketsSold())
        .build();
  }

  @Override
  public EventDto updateEvet(Long id, EventDto eventDto) {
    if (eventDto.getStartDate().isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("La fecha de Inicio no puede ser anterior a la fecha actual");
    }
    if (eventDto.getEndDate().isBefore(eventDto.getStartDate())) {
      throw new IllegalArgumentException("La fecha final no puede ser anterior a la fecha inicial");
    }
    if (eventDto.getTotalTicket() < 1 || eventDto.getTotalTicket() > 300) {
      throw new IllegalArgumentException("El total de boletos debe ser entre 1 y 300");
    }
    Optional<Event> eventExist = eventRepository.findByName(eventDto.getName());
    if (eventExist.isPresent()) {
      throw new IllegalArgumentException("Ya existe un evento con ese nombre");
    }
    Optional<Event> eventActualOp = eventRepository.findById(id);
    if (eventActualOp.isEmpty()) {
      throw new IllegalArgumentException("El evento no Existe");
    }
    if (eventActualOp.get().getTicketsSold().compareTo(eventDto.getTotalTicket()) > 0) {
      throw new IllegalArgumentException("El total de boletos es menor a los boletos vendidos");
    }

    Event eventActual = eventActualOp.get();
    eventActual.setName(eventDto.getName());
    eventActual.setStartDate(eventDto.getStartDate());
    eventActual.setEndDate(eventDto.getEndDate());
    eventActual.setTotalTicket(eventDto.getTotalTicket());

    Event eventUpdated = eventRepository.save(eventActual);

    return EventDto.builder()
        .id(eventUpdated.getId())
        .name(eventUpdated.getName())
        .startDate(eventUpdated.getStartDate())
        .endDate(eventUpdated.getEndDate())
        .totalTicket(eventUpdated.getTotalTicket())
        .ticketsSold(eventUpdated.getTicketsSold())
        .build();
  }

  @Override
  public void deleteEventById(Long id) {
    Optional<Event> eventOp = eventRepository.findById(id);
    if (eventOp.isEmpty()) {
      throw new IllegalArgumentException("El evento no Existe");
    }
    Event event = eventOp.get();
    if (event.getTicketsSold() > 0 && event.getEndDate().isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("No se puede eliminar el Evento con boletos vendidos");
    }
    eventRepository.delete(eventOp.get());
  }
}
