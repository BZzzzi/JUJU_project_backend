package com.juju.JUJU_project_backend.Controller;

import com.juju.JUJU_project_backend.DTO.PasswordChangeDto;
import com.juju.JUJU_project_backend.Service.PasswordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
public class PasswordChangeController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto passwordChangeDto){
        try{
            passwordService.changePassword(passwordChangeDto.getEmail(), passwordChangeDto);
            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Password change failed"));
        }
    }
}

