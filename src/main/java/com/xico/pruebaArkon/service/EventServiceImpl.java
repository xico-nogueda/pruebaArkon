package com.xico.pruebaArkon.service;

import com.xico.pruebaArkon.dto.EventDto;
import com.xico.pruebaArkon.entity.Event;
import com.xico.pruebaArkon.repository.EventRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

  private final EventRepository eventRepository;

  @Override
  public EventDto getEventById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEventById'");
  }

  @Override
  public EventDto createEvent(EventDto eventDto) {
    return null;
  }

  @Override
  public EventDto updateEvet(Long id, EventDto eventDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateEvet'");
  }

  @Override
  public void deleteEventById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteEvent'");
  }
}
