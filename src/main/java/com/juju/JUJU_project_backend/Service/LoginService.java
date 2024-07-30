package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.DTO.LoginRequestDto;
import com.juju.JUJU_project_backend.DTO.LoginResponseDto;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Entity.Token;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import com.juju.JUJU_project_backend.Repository.TokenRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        logger.debug("Attempting login for email: {}", loginRequestDto.getEmail());

        Optional<Token> userOpt = tokenRepository.findByEmail(loginRequestDto.getEmail());
        if (userOpt.isPresent()) {
            Token token = userOpt.get();
            boolean isMatch = passwordEncoder.matches(loginRequestDto.getPassword(), token.getPassword());
            if (isMatch) {
                logger.info("Login successful for email: {}", loginRequestDto.getEmail());
                return createLoginResponseDto(token);
            } else {
                logger.warn("Invalid password for email: {}", loginRequestDto.getEmail());
                throw new IllegalArgumentException("Invalid email or password");
            }
        } else {
            logger.warn("User not found for email: {}", loginRequestDto.getEmail());
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
    public LoginResponseDto getUserInfo(String email) {
        Optional<Token> userOpt = tokenRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            Token token = userOpt.get();
            return createLoginResponseDto(token);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public boolean isValidUser(String email) {
        return tokenRepository.findByEmail(email).isPresent();
    }

    private LoginResponseDto createLoginResponseDto(Token token) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setEmail(token.getEmail());
        loginResponseDto.setFullName(token.getUsername());
        loginResponseDto.setNickname(token.getNickname());
        return loginResponseDto;
    }
}

