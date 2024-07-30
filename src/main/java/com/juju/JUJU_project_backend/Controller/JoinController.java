package com.juju.JUJU_project_backend.Controller;

import com.juju.JUJU_project_backend.DTO.SendEmailDto;
import com.juju.JUJU_project_backend.DTO.VerifyEmailDto;
import com.juju.JUJU_project_backend.DTO.JoinDto;
import com.juju.JUJU_project_backend.Service.CookieService;
import com.juju.JUJU_project_backend.Service.JoinService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class JoinController {

    @Autowired
    private JoinService joinService;

    @Autowired
    private CookieService cookieService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody JoinDto joinDto) {
        log.info("registerUser called with: {}", joinDto);
        try {
            String encryptedEmail = joinService.registerUser(joinDto);
            return ResponseEntity.ok("Registration successful.");
        } catch (Exception e) {
            log.error("Registration failed for email: {}", joinDto.getEmail(), e);
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/verify/{encryptedToken}")
    public ResponseEntity<String> verifyUser(@PathVariable String encryptedToken) {
        log.info("verifyUser called with encrypted token: {}", encryptedToken);
        try {
            joinService.verifyUser(encryptedToken);
            return ResponseEntity.ok("Email verification successful!");
        } catch (Exception e) {
            log.error("Email verification failed for encrypted token: {}", encryptedToken, e);
            return ResponseEntity.badRequest().body("Email verification failed: " + e.getMessage());
        }
    }

    @PostMapping("/send-email-verification")
    public ResponseEntity<String> sendEmailVerification(@RequestBody SendEmailDto sendEmailDto) {
        log.info("sendEmailVerification called with email: {}", sendEmailDto.getEmail());
        try {
            joinService.sendEmailVerification(sendEmailDto.getEmail());
            return ResponseEntity.ok("Verification email sent successfully.");
        } catch (Exception e) {
            log.error("Failed to send verification email: {}", sendEmailDto.getEmail(), e);
            return ResponseEntity.badRequest().body("Failed to send verification email: " + e.getMessage());
        }
    }

    @PostMapping("/verify-email-code")
    public ResponseEntity<Boolean> verifyEmailCode(@RequestBody VerifyEmailDto verifyEmailDto) {
        log.info("verifyEmailCode called with email: {}", verifyEmailDto.getEmail());
        boolean verified = joinService.verifyEmailCode(verifyEmailDto);
        if (verified) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
