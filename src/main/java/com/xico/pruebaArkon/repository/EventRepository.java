package com.xico.pruebaArkon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xico.pruebaArkon.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
