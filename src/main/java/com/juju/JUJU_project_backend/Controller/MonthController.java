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

    @GetMapping("/todo")
    public List<EventsDto> monthPlan(@CookieValue(name = "userEmail", required = false) String cookieEmail,
                                     @RequestParam(name = "email", required = false) String email) {
        // 쿠키에서 이메일을 가져오거나, 없으면 파라미터에서 가져옵니다.
        String userEmail = cookieEmail != null ? cookieEmail : email;

        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("User email is required");
        }
        List<Events> eventsByEmail = eventsService.getEventsByEmail(userEmail);
        return eventsByEmail.stream()
                .map(EventsConverter::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/todo/add")
    public EventsDto addEvent(@RequestBody EventsDto eventDto, @CookieValue(name = "userEmail") String email) {
        Events event = EventsConverter.toEntity(eventDto);
        event.setEmail(email);
        Events savedEvent = eventsService.addEvent(event);
        return EventsConverter.toDto(savedEvent);
    }

    @PutMapping("/todo/update")
    public EventsDto updateEvent(@RequestBody EventsDto eventDto, @CookieValue(name = "userEmail") String email) {
        Events updatedEvent = eventsService.updateEvent(email, eventDto);
        return EventsConverter.toDto(updatedEvent);
    }

    @DeleteMapping("/todo/delete")
    public void deleteEvent(@RequestBody EventsDto eventDto, @CookieValue(name = "userEmail") String email) {
        eventsService.deleteEvent(email, eventDto.getTitle());
    }

}
