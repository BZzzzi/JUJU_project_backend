package com.juju.JUJU_project_backend.Controller;

import com.juju.JUJU_project_backend.DTO.JoinDto;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Service.JoinService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Log4j2
public class JoinController {
    @Autowired
    private JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<MainOption> joinUser(@RequestBody JoinDto joinDto) {
        try {
            MainOption user = joinService.joinUser(joinDto);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
