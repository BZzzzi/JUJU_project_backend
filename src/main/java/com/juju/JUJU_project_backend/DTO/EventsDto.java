package com.juju.JUJU_project_backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventsDto {
    private String email;

    private String title;

    private LocalDateTime start;

    private LocalDateTime end;

    private String color;
}
