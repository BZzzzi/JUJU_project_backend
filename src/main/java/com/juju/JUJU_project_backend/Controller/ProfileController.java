package com.juju.JUJU_project_backend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Service.MainOptionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@Log4j2
public class ProfileController {

    @Autowired
    private MainOptionService mainOptionService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadProfileImg(@RequestPart("profilePicture") MultipartFile file,
                                              @RequestPart("data") String dataJson){
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "파일이 비어 있습니다."));
        }
        try {
            MainOption data = objectMapper.readValue(dataJson, MainOption.class);
            String username = data.getUsername();
            String imageUrl = mainOptionService.updateProfileImg(username, file);
            return ResponseEntity.ok(Map.of("url", imageUrl));
        } catch (Exception e) {
            log.error("Error uploading profile picture: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "프로필 사진 업로드를 실패하였습니다."));
        }
    }
}
