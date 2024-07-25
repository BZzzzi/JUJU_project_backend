package com.juju.JUJU_project_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email_id;
    private String nickname;
    private String profile_img_name;
    private String profile_img_path;
}
