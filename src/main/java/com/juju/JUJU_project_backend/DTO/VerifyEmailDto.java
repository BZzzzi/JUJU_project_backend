package com.juju.JUJU_project_backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyEmailDto {
    private String email;
    private String code;
}
