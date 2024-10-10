package com.xico.pruebaArkon.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import com.xico.pruebaArkon.DataProviderMock;
import com.xico.pruebaArkon.dto.TicketDto;
import com.xico.pruebaArkon.entity.Event;
import com.xico.pruebaArkon.entity.Ticket;
import com.xico.pruebaArkon.repository.EventRepository;
import com.xico.pruebaArkon.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
public class TicketChangeServiceTest {

  @Mock
  private TicketRepository ticketRepository;

  private EventRepository eventRepository;

  @InjectMocks
  private TicketServiceImpl ticketService;

  @Test
  public void changeTicketSuccess() {
    Long idTicket = 1L;

    when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(DataProviderMock.ticketToChangeMock()));
    when(ticketRepository.save(any(Ticket.class))).thenReturn(DataProviderMock.ticketChangedMock());

    TicketDto ticketDto = ticketService.changeTicket(idTicket);

    assertDoesNotThrow(() -> ticketService.changeTicket(idTicket));
    verify(ticketRepository).save(any(Ticket.class));
    assertEquals(1L, ticketDto.getId());
    assertEquals(1L, ticketDto.getIdEvent());
    assertTrue(ticketDto.isTicketChanged());
  }

  @Test
  public void shouldThrowExceptionIf_currentDate_isBefore_ofStarDateOfEvent() {
    Long idTicket = 1L;
    Event event = Event.builder()
        .id(1L)
        .name("EventMock")
        .startDate(LocalDate.now().plusDays(10))
        .endDate(LocalDate.now().plusDays(20))
        .totalTicket(100)
        .ticketsSold(50)
        .build();

    when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> ticketService.changeTicket(idTicket));

    assertEquals(
        "Un boleto solo puede ser canjeado 1 vez y únicamente dentro de los limites de inicio y fin del evento al que pertenece",
        exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionIf_currentDate_isAfter_oEndDateOfEvent() {
    Long idTicket = 1L;
    Event event = Event.builder()
        .id(1L)
        .name("EventMock")
        .startDate(LocalDate.now().minusDays(20))
        .endDate(LocalDate.now().minusDays(10))
        .totalTicket(100)
        .ticketsSold(100)
        .build();

    when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> ticketService.changeTicket(idTicket));

    assertEquals(
        "Un boleto solo puede ser canjeado 1 vez y únicamente dentro de los limites de inicio y fin del evento al que pertenece",
        exception.getMessage());
  }

}
