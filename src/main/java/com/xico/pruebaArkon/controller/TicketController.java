package com.xico.pruebaArkon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xico.pruebaArkon.dto.TicketDto;
import com.xico.pruebaArkon.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Tag(name = "Ticket", description = "Controller for Tickets")
public class TicketController {
  
  private final TicketService ticketService;

  @Operation(
    summary = "Create a new Ticket",
    description = "Create a new Ticket with the idEvent given",
    tags = {"Ticket"},
    method = "POST",
    parameters = {
      @Parameter(
        name = "idEvent",
        description = "Id of the event we want to create ticket",
        required = true
      )
    },
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Successful",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = TicketDto.class)
        )
      )
    }
  )
  @PostMapping("/{idEvent}")
  public ResponseEntity<TicketDto> sellTicket(@PathVariable Long idEvent){
    TicketDto ticketDto = ticketService.sellTicket(idEvent);
    if(ticketDto == null){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(ticketDto, HttpStatus.OK);
  }

  @Operation(
    summary = "Update a Ticket",
    description = "Changes the status of the 'redeemed' parameter of the ticket with the given Id",
    tags = {"Ticket"},
    method = "PUT",
    parameters = {
      @Parameter(
        name = "id",
        description = "Id of the ticket we want to redeemed",
        required = true
      )
    },
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Successful",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = TicketDto.class)
        )
      )
    }
  )
  @PutMapping("/{id}")
  public ResponseEntity<TicketDto> changeTicket(@PathVariable Long id){
    TicketDto ticketDto = ticketService.changeTicket(id);
    if(ticketDto == null){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(ticketDto, HttpStatus.OK);
  }

}
