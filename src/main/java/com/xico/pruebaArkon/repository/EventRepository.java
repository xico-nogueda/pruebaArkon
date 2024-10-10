package com.xico.pruebaArkon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xico.pruebaArkon.entity.Event;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
  Optional<Event> findByName(String name);
}
