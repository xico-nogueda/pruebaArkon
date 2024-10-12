package com.xico.pruebaArkon.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.time.LocalDate;

import com.xico.pruebaArkon.DataProviderMock;
import com.xico.pruebaArkon.dto.TicketDto;
import com.xico.pruebaArkon.entity.Event;
import com.xico.pruebaArkon.entity.Ticket;
import com.xico.pruebaArkon.repository.EventRepository;
import com.xico.pruebaArkon.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
public class TicketSellServiceTest {

  @Mock
  private TicketRepository ticketRepository;

  @Mock
  private EventRepository eventRepository;

  @InjectMocks
  private TicketServiceImpl ticketService;

  @Test
  public void sellTicketSuccess() {
    Long idEvent = 1L;
    Event eventMock = Event.builder()
        .id(1L)
        .name("Event Mock")
        .startDate(LocalDate.now().minusDays(10))
        .endDate(LocalDate.now().plusDays(10))
        .totalTicket(200)
        .ticketsSold(100)
        .build();

    when(eventRepository.findById(anyLong())).thenReturn(Optional.of(eventMock));
    when(ticketRepository.save(any(Ticket.class))).thenReturn(DataProviderMock.ticketSoldMock());

    TicketDto ticketSold = ticketService.sellTicket(idEvent);

    assertDoesNotThrow(() -> ticketService.sellTicket(idEvent));
    assertEquals(1L, ticketSold.getId());
    assertEquals(1L, ticketSold.getIdEvent());
    assertFalse(ticketSold.isTicketChanged());
  }

  @Test
  public void shouldThrowExceptionIf_eventNotExist() {
    Long idEvent = 1L;

    when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> ticketService.sellTicket(idEvent));

    assertEquals("El evento no Existe", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionIf_thereAreNot_ticketsAvailable() {
    Long idEvent = 1L;
    Event eventMock = Event.builder()
        .id(1L)
        .name("Event Mock")
        .startDate(LocalDate.now().minusDays(10))
        .endDate(LocalDate.now().plusDays(10))
        .totalTicket(200)
        .ticketsSold(200)
        .build();

    when(eventRepository.findById(anyLong())).thenReturn(Optional.of(eventMock));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> ticketService.sellTicket(idEvent));

    assertEquals("No existen boletos disponibles para el Evento elegido", exception.getMessage());
  }

}
