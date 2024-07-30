package com.juju.JUJU_project_backend.Controller;

import com.juju.JUJU_project_backend.DTO.LoginResponseDto;
import com.juju.JUJU_project_backend.Service.LoginService;
import com.juju.JUJU_project_backend.Utill.EncryptionUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class UserPageController {
    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private LoginService loginService;

    @GetMapping("/main")
    public ResponseEntity<?> userPage(@CookieValue(name = "userEmail", required = false) String encryptedEmail, HttpServletRequest request) {
        log.info("Received request to /main with cookie: {}", encryptedEmail);
        System.out.println("Received request to /main with cookie: " + encryptedEmail);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("Received cookie: {}={}", cookie.getName(), cookie.getValue());
            }
        } else {
            log.info("No cookies received");
        }
        try {
            // 1. 요청 데이터에 쿠키가 없는 경우
            if (encryptedEmail == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("쿠키가 없습니다. 로그인이 필요합니다.");
            }

            // 쿠키 디코딩
            String email = encryptionUtil.decrypt(encryptedEmail);

            // 2. 쿠키를 디코딩 했을 때 나온 값이 테이블의 user_email 중에 일치하는 값이 없는 경우
            if (!loginService.isValidUser(email)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("유효하지 않은 사용자입니다.");
            }

            // 위 두 경우에 해당하지 않을 경우, 유저 정보를 반환
            LoginResponseDto userInfo = loginService.getUserInfo(email);
            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            log.error("사용자 페이지 접근 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }
}
