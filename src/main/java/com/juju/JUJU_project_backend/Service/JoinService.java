package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.DTO.JoinDto;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JoinService {
    private MainOptionRepository mainOptionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MainOption joinUser(JoinDto joinDto) {
        if (!joinDto.getPassword1().equals(joinDto.getPassword2())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        MainOption user = MainOption.builder()
                .email(joinDto.getEmail())
                .username(joinDto.getUsername())
                .password(passwordEncoder.encode(joinDto.getPassword1()))
                .nickname(joinDto.getNickname())
                .birthday(joinDto.getBirthday())
                .build();

        return mainOptionRepository.save(user);
    }
}
