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
import java.util.List;
import com.xico.pruebaArkon.DataProviderMock;
import com.xico.pruebaArkon.dto.TicketDto;
import com.xico.pruebaArkon.entity.Ticket;
import com.xico.pruebaArkon.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
public class TicketSellServiceTest {

  @Mock
  private TicketRepository ticketRepository;

  @InjectMocks
  private TicketServiceImpl ticketService;

  @Test
  public void sellTicketSuccess() {
    Long idEvent = 1L;

    when(ticketRepository.findByEventId(anyLong())).thenReturn(DataProviderMock.listNewTicketsMocks());
    when(ticketRepository.save(any(Ticket.class))).thenReturn(DataProviderMock.ticketSoldMock());

    TicketDto ticketSold = ticketService.sellTicket(idEvent);

    assertDoesNotThrow(() -> ticketService.sellTicket(idEvent));
    assertEquals(1L, ticketSold.getId());
    assertEquals(1L, ticketSold.getIdEvent());
    assertFalse(ticketSold.isTicketChanged());
  }

  @Test
  public void shouldThrowExceptionIf_thereAreNot_tickets_forEvent() {
    Long idEvent = 1L;

    when(ticketRepository.findByEventId(anyLong())).thenReturn(List.of());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> ticketService.sellTicket(idEvent));

    assertEquals("No existen boletos para el Evento", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionIf_thereAreNot_ticketsAvailable() {
    Long idEvent = 1L;

    when(ticketRepository.findByEventId(anyLong())).thenReturn(DataProviderMock.listTickets_withOut_ticketsAvailable());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> ticketService.sellTicket(idEvent));

    assertEquals("No existen boletos disponibles para el Evento elegido", exception.getMessage());
  }

}
