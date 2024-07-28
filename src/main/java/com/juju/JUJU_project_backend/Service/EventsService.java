package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.DTO.EventsDto;
import com.juju.JUJU_project_backend.Entity.Events;

import java.util.List;

public interface EventsService {
    List<Events> getAllEvents(); //모든 이벤트 받아오기

    Events updateEvent(int id, EventsDto eventDto); //이벤트 업데이트

    Events addEvent(Events event); //이벤트추가

    void deleteEvent(int id); //이벤트 삭제


}
