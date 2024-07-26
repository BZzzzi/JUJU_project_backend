package com.juju.JUJU_project_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class MainOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String username;
    private String password;
    private String nickname;
    private String birthday;
    private String profile_img_name;
    private String profile_img_path;
}
