package com.juju.JUJU_project_backend.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.juju.JUJU_project_backend.dto.EventsDto;
import com.juju.JUJU_project_backend.entity.Events;
import com.juju.JUJU_project_backend.service.EventsService;
import com.juju.JUJU_project_backend.Util.EventsConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/api/todo")
@RestController
@Log4j2
public class MonthController {

    private final EventsService eventsService;

    @Autowired
    public MonthController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    public List<EventsDto> monthPlan() {
        List<Events> eventsAll = eventsService.getAllEvents();
        return eventsAll.stream()
                .map(EventsConverter::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public EventsDto addEvent(@RequestBody EventsDto eventDto) {
        Events event = EventsConverter.toEntity(eventDto);
        Events savedEvent = eventsService.addEvent(event);
        return EventsConverter.toDto(savedEvent);
    }

    @PutMapping("/{id}")
    public EventsDto updateEvent(@PathVariable int id, @RequestBody EventsDto eventDto) {
        Events updatedEvent = eventsService.updateEvent(id, eventDto);
        return EventsConverter.toDto(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable int id){
        eventsService.deleteEvent(id);
    }

}