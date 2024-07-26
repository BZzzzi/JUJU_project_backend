package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Transactional
public class MainOptionService {

    @Autowired
    private MainOptionRepository mainOptionRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String updateProfileImg(String username, MultipartFile file) throws Exception {

        System.out.println("Upload directory: " + uploadDir);

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;


//            Path uploadPath = Paths.get(resourceLoader.getResource(uploadDir).getURI()).toAbsolutePath().normalize();
            Path uploadPath = Paths.get("src/main/resources/static/file");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            MainOption mainOption = mainOptionRepository.findByUsername(username);
            if (mainOption == null) {
                mainOption = new MainOption();
                mainOption.setUsername(username);
            }

            mainOption.setProfile_img_name(uniqueFileName);
            String imageUrl = "/file/" + uniqueFileName; // URL 경로 설정
            mainOption.setProfile_img_path(imageUrl);
            mainOptionRepository.save(mainOption);

            return imageUrl;
        } catch (IOException e) {
            throw new Exception("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
