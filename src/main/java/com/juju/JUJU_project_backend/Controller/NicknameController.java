package com.juju.JUJU_project_backend.Controller;

import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Service.MainOptionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
public class NicknameController {
    @Autowired
    private MainOptionService mainOptionService;

    @GetMapping("/{id}")
    public MainOption getMainOption(@PathVariable("id") int id) {
        return mainOptionService.getMainOption(id);
    }

    @PutMapping("/{id}/nickname") // http://localhost:8080/api/option/1/nickname?nickname=새로운닉네임
    public MainOption updateNickname(@PathVariable("id") int id, @RequestBody Map<String, String> request) {
        String nickname = request.get("nickname");
        return mainOptionService.updateNickname(id, nickname);
    }
}
