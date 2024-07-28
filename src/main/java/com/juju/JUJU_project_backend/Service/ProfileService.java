package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.DTO.PasswordChangeDto;
import com.juju.JUJU_project_backend.DTO.ProfileDto;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private MainOptionRepository mainOptionRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProfileDto updateProfileImg(String email, MultipartFile file) throws Exception {

        System.out.println("Upload directory: " + uploadDir);

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
//          Path uploadPath = Paths.get("src/main/resources/static/file");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Optional<MainOption> mainOption = mainOptionRepository.findByEmail(email);
            if (mainOption.isEmpty()) {
                throw new Exception("유저를 찾을 수 없습니다.");
            }

            MainOption user = mainOption.get();
            user.setProfile_img_name(uniqueFileName);
            String imageUrl = "http://localhost:8080/file/" + uniqueFileName; // URL 경로 설정
            user.setProfile_img_path(imageUrl);
            mainOptionRepository.save(user);

            ProfileDto profileDto = new ProfileDto();
            profileDto.setEmail(user.getEmail());
            profileDto.setProfileImgName(user.getProfile_img_name());
            profileDto.setProfileImgPath(user.getProfile_img_path());

            return profileDto;
        } catch (IOException e) {
            throw new Exception("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    public MainOption findByEmail(String email) {
        return mainOptionRepository.findByEmail(email).orElse(null);
    }
}
