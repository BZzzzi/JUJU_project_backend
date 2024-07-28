package com.juju.JUJU_project_backend.Controller;

import com.juju.JUJU_project_backend.DTO.NicknameDto;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Service.NicknameService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
public class NicknameController {
    @Autowired
    private NicknameService nicknameService;

    @GetMapping("/{email}")
    public MainOption getMainOption(@PathVariable("email") String email) {
        return nicknameService.getMainOption(email);
    }

    @PutMapping("/{email}/nickname") // http://localhost:8080/api/email/nickname?nickname=새로운닉네임
    public MainOption updateNickname(@PathVariable("email") String email, @RequestBody NicknameDto nicknameDto) {
        return nicknameService.updateNickname(email, nicknameDto.getNickname());
    }
}
