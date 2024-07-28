package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.DTO.LoginRequestDto;
import com.juju.JUJU_project_backend.DTO.LoginResponseDto;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class LoginService {
    @Autowired
    private MainOptionRepository mainOptionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Optional<MainOption> userOpt = mainOptionRepository.findByEmail(loginRequestDto.getEmail());
        if (userOpt.isPresent()) {
            MainOption user = userOpt.get();
            if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                LoginResponseDto loginResponseDto = new LoginResponseDto();
                loginResponseDto.setEmail(user.getEmail());
                loginResponseDto.setUsername(user.getUsername());
                loginResponseDto.setNickname(user.getNickname());
                loginResponseDto.setProfileImgPath(user.getProfile_img_path());
                return loginResponseDto;
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}
