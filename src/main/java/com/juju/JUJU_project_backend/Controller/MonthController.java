package com.juju.JUJU_project_backend.Controller;

import com.juju.JUJU_project_backend.DTO.EventsDto;
import com.juju.JUJU_project_backend.Entity.Events;
import com.juju.JUJU_project_backend.Service.EventsService;
import com.juju.JUJU_project_backend.Utill.EventsConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
@Log4j2
public class MonthController {

    private final EventsService eventsService;

    @Autowired
    public MonthController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping("/event/{id}")
    public List<EventsDto> monthPlan() {
        List<Events> eventsAll = eventsService.getAllEvents();
        return eventsAll.stream()
                .map(EventsConverter::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/event/add/{id}")
    public EventsDto addEvent(@RequestBody EventsDto eventDto) {
        Events event = EventsConverter.toEntity(eventDto);
        Events savedEvent = eventsService.addEvent(event);
        return EventsConverter.toDto(savedEvent);
    }

    @PutMapping("/event/update/{id}")
    public EventsDto updateEvent(@PathVariable int id, @RequestBody EventsDto eventDto) {
        Events updatedEvent = eventsService.updateEvent(id, eventDto);
        return EventsConverter.toDto(updatedEvent);
    }

    @DeleteMapping("/event/delete/{id}")
    public void deleteEvent(@PathVariable int id){
        eventsService.deleteEvent(id);
    }

}
