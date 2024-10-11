package com.xico.pruebaArkon.service;

import com.xico.pruebaArkon.dto.EventDto;
import com.xico.pruebaArkon.entity.Event;
import com.xico.pruebaArkon.repository.EventRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventGetServiceTest {

  @Mock
  private EventRepository eventRepository;

  @InjectMocks
  private EventServiceImpl eventService;

  @Test
  public void getEventSuccess() {
    Long id = 1L;
    Event eventMock = Event.builder()
        .id(1L)
        .name("Evento Mock")
        .startDate(LocalDate.now().minusDays(30))
        .endDate(LocalDate.now().minusDays(1))
        .totalTicket(200)
        .ticketsSold(100)
        .build();

    when(eventRepository.findById(id)).thenReturn(Optional.of(eventMock));

    EventDto eventDto = eventService.getEventById(id);

    assertTrue(eventDto != null);
    assertTrue(eventDto.getId() > 0);
    assertTrue(eventDto.getName() != null);
    assertTrue(eventDto.getStartDate() != null);
    assertTrue(eventDto.getEndDate() != null);
    assertTrue(eventDto.getTotalTicket() != null);
    assertTrue(eventDto.getTicketsSold() != null);
  }

  @Test
  public void shouldThrowExceptionIf_eventNotExist() {
    Long id = 1L;

    when(eventRepository.findById(id)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> eventService.getEventById(id));

    assertEquals("El evento no Existe", exception.getMessage());
  }

}
