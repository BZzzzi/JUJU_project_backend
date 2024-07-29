package com.juju.JUJU_project_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String birthday;
}
