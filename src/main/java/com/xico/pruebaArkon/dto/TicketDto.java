package com.xico.pruebaArkon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TicketDto {
  private Long id;
  private boolean ticketChanged;
  private Long idEvent;
}
