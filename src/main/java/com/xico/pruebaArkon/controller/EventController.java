package com.xico.pruebaArkon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xico.pruebaArkon.dto.EventDto;
import com.xico.pruebaArkon.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Event", description = "Controller for Events")
public class EventController {

  private final EventService eventService;

  @Operation(
    summary = "Get Event by and Id",
    description = "Gets the details of an event from a given Id ",
    tags = {"Event"},
    method = "GET",
    parameters = {
      @Parameter(
        name = "id",
        description = "Id of the event we want to get",
        required = true
      )
    },
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Successful",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = EventDto.class)
        )
      )
    }
  )
  @GetMapping("/{id}")
  public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
    EventDto eventDto = eventService.getEventById(id);
    if (eventDto == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } 
    return new ResponseEntity<>(eventDto, HttpStatus.OK);
  }

  @Operation(
    summary = "Create a new Event",
    description = "Create a new event from the information provided ",
    tags = {"Event"},
    method = "POST",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "Create Event request with: name, startDate, endDate and totalTickets (ticketSold and id parameters are no required)",
      required = true,
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = EventDto.class)
      )
    ),
    responses = {
      @ApiResponse(
        responseCode = "201",
        description = "Successful",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = EventDto.class)
        )
      )
    }
  )
  @PostMapping
  public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
    EventDto eventDtoCreated = eventService.createEvent(eventDto);
    if (eventDtoCreated == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(eventDtoCreated, HttpStatus.CREATED);
  }
  
  @Operation(
    summary = "Update and Event",
    description = "Update an event from the information and Id provided",
    tags = {"Event"},
    method = "PUT",
    parameters = {
      @Parameter(
        name = "id",
        description = "Id of the event we want to update",
        required = true
      )
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "Update Event request with: name, startDate, endDate and totalTickets (ticketSold and id parameters are no required)",
      required = true,
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = EventDto.class)
      )
    ),
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Successful",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = EventDto.class)
        )
      )
    }
  )
  @PutMapping("/{id}")
  public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto){
    EventDto eventDtoUpdated = eventService.updateEvet(id, eventDto);
    if(eventDtoUpdated == null){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(eventDtoUpdated, HttpStatus.OK);
  }

  @Operation(
    summary = "Delete an event by an Id",
    description = "Delete an event from a given Id ",
    tags = {"Event"},
    method = "DELETE",
    parameters = {
      @Parameter(
        name = "id",
        description = "Id of the event we want to delete",
        required = true
      )
    },
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Successful",
        content = @Content(
          mediaType = "text/plain",
          schema = @Schema(implementation = String.class)
        )
      )
    }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEventById(@PathVariable Long id) {
    eventService.deleteEventById(id);
    return new ResponseEntity<>("Successfully Delete", HttpStatus.OK);
  }

}
