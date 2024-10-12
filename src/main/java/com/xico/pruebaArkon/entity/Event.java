package com.xico.pruebaArkon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "event")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_Date", nullable = false)
  private LocalDate endDate;

  @Column(name = "total_ticket", nullable = false)
  private Integer totalTicket;

  @Column(name = "tickets_sold", nullable = false)
  private Integer ticketsSold;
}
