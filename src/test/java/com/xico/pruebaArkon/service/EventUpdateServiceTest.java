package com.xico.pruebaArkon.service;

import com.xico.pruebaArkon.DataProviderMock;
import com.xico.pruebaArkon.dto.EventDto;
import com.xico.pruebaArkon.entity.Event;
import com.xico.pruebaArkon.repository.EventRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventUpdateServiceTest {

  @Mock
  private EventRepository eventRepository;

  @InjectMocks
  private EventServiceImpl eventService;

  @Test
  public void updateEventSuccess() {
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusDays(30);
    EventDto eventDto = EventDto.builder()
        .name("Event Mock")
        .startDate(startDate)
        .endDate(endDate)
        .totalTicket(200)
        .build();

    when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(DataProviderMock.eventMock()));
    when(eventRepository.save(any(Event.class))).thenReturn(DataProviderMock.eventMock());

    EventDto eventSaved = eventService.updateEvet(10L, eventDto);

    verify(eventRepository).save(any(Event.class));
    assertEquals(10L, eventSaved.getId());
    assertEquals("Event Mock", eventSaved.getName());
    assertEquals(startDate, eventSaved.getStartDate());
    assertEquals(endDate, eventSaved.getEndDate());
    assertEquals(200, eventSaved.getTotalTicket());
  }

  @Test
  public void shouldThrowExceptionIf_nameExist() {
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusDays(30);
    EventDto eventDto = EventDto.builder()
        .name("Event Mock")
        .startDate(startDate)
        .endDate(endDate)
        .totalTicket(200)
        .build();

    when(eventRepository.findByName(any(String.class))).thenReturn(Optional.of(DataProviderMock.eventMock()));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> eventService.updateEvet(10L, eventDto));

    assertEquals("Ya existe un evento con ese nombre", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionIf_startDate_isBefore_currentDate() {
    LocalDate startDate = LocalDate.now().minusDays(1);
    LocalDate endDate = LocalDate.now().plusDays(30);
    EventDto eventDto = EventDto.builder()
        .name("Event Mock")
        .startDate(startDate)
        .endDate(endDate)
        .totalTicket(100)
        .build();

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> eventService.updateEvet(10L, eventDto));

    assertEquals("La fecha de Inicio no puede ser anterior a la fecha actual", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionIf_endDate_isBefore_startDate() {
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().minusDays(10);
    EventDto eventDto = EventDto.builder()
        .name("Event Mock")
        .startDate(startDate)
        .endDate(endDate)
        .totalTicket(100)
        .build();

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> eventService.updateEvet(10L, eventDto));
    assertEquals("La fecha final no puede ser anterior a la fecha inicial", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionIf_ticketsSold_exceeds_newTotalTickets() {
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusDays(30);
    EventDto eventDto = EventDto.builder()
        .name("Event Mock")
        .startDate(startDate)
        .endDate(endDate)
        .totalTicket(50)
        .build();
    when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(DataProviderMock.eventMock()));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> eventService.updateEvet(10L, eventDto));

    assertEquals("El total de boletos es menor a los boletos vendidos", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionIf_tickets_areLessThanOne() {
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusDays(30);
    EventDto eventDto = EventDto.builder()
        .name("Event Mock")
        .startDate(startDate)
        .endDate(endDate)
        .totalTicket(0)
        .build();

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> eventService.updateEvet(10L, eventDto));

    assertEquals("El total de boletos debe ser entre 1 y 300", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionIf_tickets_areMoreThan300() {
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusDays(30);
    EventDto eventDto = EventDto.builder()
        .name("Event Mock")
        .startDate(startDate)
        .endDate(endDate)
        .totalTicket(301)
        .build();

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> eventService.updateEvet(10L, eventDto));

    assertEquals("El total de boletos debe ser entre 1 y 300", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionIf_eventNotExist() {
    Long id = 10L;
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusDays(30);
    EventDto eventDto = EventDto.builder()
        .name("Event Mock")
        .startDate(startDate)
        .endDate(endDate)
        .totalTicket(200)
        .build();

    when(eventRepository.findById(id)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> eventService.updateEvet(10L, eventDto));

    assertEquals("El evento no Existe", exception.getMessage());
  }

}
