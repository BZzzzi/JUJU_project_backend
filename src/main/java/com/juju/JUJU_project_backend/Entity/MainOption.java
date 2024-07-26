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
@Table(name = "optionprofile")
public class MainOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String email_id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profile_img_name;

    @Column(nullable = false)
    private String profile_img_path;
}
