package com.juju.JUJU_project_backend.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CookieService {
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
        response.addCookie(cookie);
        log.info("Cookie added to response: {}={}", name, value);
    }
}
