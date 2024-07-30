package com.juju.JUJU_project_backend.Util;

import com.juju.JUJU_project_backend.dto.EventsDto;
import com.juju.JUJU_project_backend.entity.Events;

public class EventsConverter {
    public static EventsDto toDto(Events event) {
        EventsDto dto = new EventsDto();
        dto.setTitle(event.getTitle());
        dto.setStart(event.getStart());
        dto.setEnd(event.getEnd());
        dto.setColor(event.getColor());
        return dto;
    }

    public static Events toEntity(EventsDto dto) {
        Events event = new Events();
        event.setTitle(dto.getTitle());
        event.setStart(dto.getStart());
        event.setEnd(dto.getEnd());
        event.setColor(dto.getColor());
        return event;
    }
}
