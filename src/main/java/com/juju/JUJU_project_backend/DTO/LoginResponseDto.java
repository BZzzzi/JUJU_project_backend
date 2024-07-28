package com.juju.JUJU_project_backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String email;
    private String username;
    private String nickname;
    private String profileImgPath;
}
