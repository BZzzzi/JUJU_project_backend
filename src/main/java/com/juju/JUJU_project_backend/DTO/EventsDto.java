package com.juju.JUJU_project_backend.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsDto {

    private String title;

    private String start;

    private String end;

    private String color;


}
