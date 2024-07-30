package com.juju.JUJU_project_backend.Controller;

import com.juju.JUJU_project_backend.DTO.LoginRequestDto;
import com.juju.JUJU_project_backend.DTO.LoginResponseDto;
import com.juju.JUJU_project_backend.Service.CookieService;
import com.juju.JUJU_project_backend.Service.LoginService;
import com.juju.JUJU_project_backend.Utill.EncryptionUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.info("Login attempt for email: {}", loginRequestDto.getEmail());
        try {
            LoginResponseDto userResponseDto = loginService.login(loginRequestDto);

            String encryptedEmail = encryptionUtil.encrypt(userResponseDto.getEmail());
            log.info("Setting cookie: userEmail={}", encryptedEmail);
            cookieService.setCookie(response, "userEmail", encryptedEmail, 18000);
            log.info("Cookie set. Checking if it's in the response: {}", response.getHeader("Set-Cookie"));
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");

            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            log.warn("Login failed for email: {}. Reason: {}", loginRequestDto.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error during login for email: {}", loginRequestDto.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An unexpected error occurred"));
        }
    }
}
