package com.juju.JUJU_project_backend.DTO;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDto {
    private String email;
    private String currentPassword;
    private String newPassword;
}
