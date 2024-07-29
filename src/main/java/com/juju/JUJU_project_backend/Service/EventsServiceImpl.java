package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.DTO.EventsDto;
import com.juju.JUJU_project_backend.Entity.Events;
import com.juju.JUJU_project_backend.Repository.EventsRepository;
import jakarta.transaction.Transactional;
import jdk.jfr.Event;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class EventsServiceImpl implements EventsService{

    private final EventsRepository eventsRepository;

    @Autowired
    public EventsServiceImpl(EventsRepository eventsRepository){
        this.eventsRepository = eventsRepository;
    }

    @Override
    public List<Events> getAllEvents() {
        return eventsRepository.findAll();
    }

    @Override
    public Events addEvent(Events events) {
        return eventsRepository.save(events);
    }

    @Override
    public void deleteEvent(int id) {
        eventsRepository.deleteById(id);
    }

    @Override
    public Events updateEvent(int id, EventsDto eventDto) {
        Optional<Events> optionalEvent = eventsRepository.findById(id);

        if (optionalEvent.isPresent()) {
            Events event = optionalEvent.get();
            event.setTitle(eventDto.getTitle());
            event.setStart(eventDto.getStart());
            event.setEnd(eventDto.getEnd());
            event.setColor(eventDto.getColor());
            return eventsRepository.save(event);
        } else {
            throw new RuntimeException("ID를 찾지 못함 : " + id);
        }
    }
}
