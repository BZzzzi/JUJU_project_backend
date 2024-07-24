package com.juju.JUJU_project_backend.Controller;


import com.juju.JUJU_project_backend.DTO.EventsDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/event")
@RestController
public class MonthController {

    @PostMapping
    public void writeEvent(EventsDto eventsDto){
        System.out.println("0090");
    }

}
