package com.juju.JUJU_project_backend.DTO;

import com.juju.JUJU_project_backend.Entity.Events;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class EventsDto {

    private String title;

    private LocalDateTime start;

    private LocalDateTime end;

    private String color;


}
