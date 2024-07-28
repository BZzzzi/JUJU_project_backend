package com.juju.JUJU_project_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private String name;

    @Column
    private String nickname;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String birthday;
}
