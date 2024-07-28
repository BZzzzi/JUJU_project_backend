package com.juju.JUJU_project_backend.Component;

import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PasswordUpdater implements CommandLineRunner {
    @Autowired
    private MainOptionRepository mainOptionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<MainOption> userOpt = mainOptionRepository.findByEmail("aaa@example.com"); // 예시 이메일
        if (userOpt.isPresent()) {
            MainOption user = userOpt.get();
            if (!user.getPassword().startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode("password")); // 예시 비밀번호
                mainOptionRepository.save(user);
            }
        }
    }
}
