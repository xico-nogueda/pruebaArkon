package com.xico.pruebaArkon.service;


import com.xico.pruebaArkon.dto.EventDto;

public interface EventService {
  public EventDto getEventById(Long id);
  public EventDto createEvent(EventDto eventDto);
  public EventDto updateEvet(Long id, EventDto eventDto);
  public void deleteEventById(Long id);
}
