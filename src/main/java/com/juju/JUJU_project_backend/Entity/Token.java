package com.juju.JUJU_project_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
public class Token {

    @Id
    @Column(nullable = false, unique = true)
    private String email;

    private String username;
    private String nickname;
    private String password;
    private LocalDate birthday;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "verification_token")
    private String verificationToken;
}
