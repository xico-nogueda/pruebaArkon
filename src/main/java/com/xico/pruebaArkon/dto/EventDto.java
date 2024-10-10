package com.xico.pruebaArkon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EventDto {
  private Long id;
  private String name;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer totalTicket;
  private Integer ticketsSold;
}
