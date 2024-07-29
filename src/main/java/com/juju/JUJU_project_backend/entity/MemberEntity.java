package com.juju.JUJU_project_backend.entity;

import com.juju.JUJU_project_backend.dto.MemberDTO;
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
    private String username;

    @Column
    private String nickname;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String birthday;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUsername(memberDTO.getUsername());
        memberEntity.setNickname(memberDTO.getNickname());
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setBirthday(memberEntity.getBirthday());
        return memberEntity;
    }
}
