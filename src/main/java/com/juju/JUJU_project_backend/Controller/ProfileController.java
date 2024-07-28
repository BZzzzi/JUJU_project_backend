package com.juju.JUJU_project_backend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/uploadProfilePicture")
    public ResponseEntity<?> uploadProfileImg(@RequestPart("profilePicture") MultipartFile file,
                                              @RequestParam("email") String email){
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "파일이 비어 있습니다."));
        }
        try {
            MainOption data = profileService.findByEmail(email);
            if (data == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "유저를 찾을 수 없습니다."));
            }
            ProfileDto profileDto = profileService.updateProfileImg(email, file);
            return ResponseEntity.ok(profileDto);
        } catch (Exception e) {
            log.error("Error uploading profile picture: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "프로필 사진 업로드를 실패하였습니다."));
        }
    }
}
