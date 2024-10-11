package com.xico.pruebaArkon.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.xico.pruebaArkon.DataProviderMock;
import com.xico.pruebaArkon.repository.TicketRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketCreateAndUpdateServiceTest {

  @Mock
  private TicketRepository ticketRepository;

  @InjectMocks
  private TicketServiceImpl ticketService;

  @Test
  public void createTicketsSuccess() {
    Long idEvent = 1L;
    Integer totalTickets = 5;

    when(ticketRepository.saveAll(any())).thenReturn(DataProviderMock.listNewTicketsMocks());

    ticketService.createTickets(idEvent, totalTickets);

    verify(ticketRepository).saveAll(anyList());
    assertDoesNotThrow(() -> ticketService.createTickets(idEvent, totalTickets));
  }

  @Test
  public void updateTicketsSuccess() {
    Long idEvent = 1L;
    Integer totalTickets = 5;

    when(ticketRepository.findByEventId(anyLong())).thenReturn(DataProviderMock.listNewTicketsMocks());

    ticketService.updateTickets(idEvent, totalTickets);

    assertDoesNotThrow(() -> ticketService.updateTickets(idEvent, totalTickets));
  }
}
