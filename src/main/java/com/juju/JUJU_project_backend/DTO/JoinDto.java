package com.juju.JUJU_project_backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {
    private String email;
    private String username;
    private String password1;
    private String password2;
    private String nickname;
    private String birthday;
}
