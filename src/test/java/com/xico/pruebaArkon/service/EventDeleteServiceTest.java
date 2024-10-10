package com.xico.pruebaArkon.service;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventDeleteServiceTest {

  @Mock
  private EventRepository eventRepository;

  @InjectMocks
  private EventServiceImpl eventService;

  @Test
  public void deleteEventSuccessWithEndDatePassed(){
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
    doNothing().when(eventRepository).delete(eventMock);

    assertDoesNotThrow(()->eventService.deleteEventById(id));
    verify(eventRepository, times(1)).delete(eventMock);
  }

  @Test
  public void deleteEventSuccessWithNoTicketsSold(){
    Long id = 1L;
    Event eventMock = Event.builder()
    .id(1L)
    .name("Evento Mock")
    .startDate(LocalDate.now().minusDays(30))
    .endDate(LocalDate.now().plusDays(10))
    .totalTicket(200)
    .ticketsSold(0)
    .build();
    
    when(eventRepository.findById(id)).thenReturn(Optional.of(eventMock));
    doNothing().when(eventRepository).delete(eventMock);

    assertDoesNotThrow(()->eventService.deleteEventById(id));
    verify(eventRepository, times(1)).delete(eventMock);
  }
  
  @Test
  public void shouldThrowExceptionIfEventNotExist(){
    Long id = 1L;

    when(eventRepository.findById(id)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
      ()->eventService.deleteEventById(id));

      assertEquals("El evento no Existe", exception.getMessage());
      verify(eventRepository, never()).delete(any(Event.class));
  }

  @Test
  public void shouldThrowExceptionIfCurrenDateIsBeforeOfEndDate(){
    Long id = 1L;
    Event eventMock = Event.builder()
    .id(1L)
    .name("Evento Mock")
    .startDate(LocalDate.now().minusDays(30))
    .endDate(LocalDate.now().plusDays(10))
    .totalTicket(200)
    .ticketsSold(0)
    .build();

    when(eventRepository.findById(id)).thenReturn(Optional.of(eventMock));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
      ()->eventService.deleteEventById(id));

      assertEquals("No se puede eliminar Evento, ya que la fecha antual es anterior a la fecha final del evento", exception.getMessage());
      verify(eventRepository, never()).delete(any(Event.class));
  }

  @Test
  public void shouldThrowExceptionIfThereAreTicketsSold(){
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

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
      ()->eventService.deleteEventById(id));

    assertEquals("No se puede eliminar el Evento con boletos vendidos", exception.getMessage());
    verify(eventRepository, never()).delete(any(Event.class));
  }
}
