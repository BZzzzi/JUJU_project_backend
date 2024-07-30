package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.DTO.PasswordChangeDto;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PasswordService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordService.class);
    @Autowired
    private MainOptionRepository mainOptionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void changePassword(String email, PasswordChangeDto passwordChangeDto) {
        MainOption user = mainOptionRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        mainOptionRepository.save(user);
    }
}