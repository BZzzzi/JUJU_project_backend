package com.juju.JUJU_project_backend.Controller;

import com.juju.JUJU_project_backend.DTO.ProfileDto;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Service.ProfileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/uploadProfilePicture")
    public ResponseEntity<?> uploadProfileImg(@RequestPart("profilePicture") MultipartFile file,
                                              @RequestParam("email") String email){
        log.info("Received request to upload profile picture for email: {}", email);
        try {
            ProfileDto profileDto = profileService.updateProfileImg(email, file);
            log.info("Profile picture updated successfully for email: {}", email);
            return ResponseEntity.ok(profileDto);
        } catch (Exception e) {
            log.error("Error uploading profile picture: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "프로필 사진 업로드를 실패하였습니다."));
        }
    }
}
