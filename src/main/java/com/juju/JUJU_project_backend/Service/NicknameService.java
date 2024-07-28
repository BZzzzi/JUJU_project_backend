package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class NicknameService {

    @Autowired
    private MainOptionRepository mainOptionRepository;

    public MainOption getMainOption(String email) {

        return mainOptionRepository.findByEmail(email).orElse(null);
    }
    public MainOption updateNickname(String email, String nickname) {
        MainOption mainOption = mainOptionRepository.findByEmail(email).orElse(null);
        if (mainOption != null) {
            mainOption.setNickname(nickname);
            mainOptionRepository.save(mainOption);
        }
        return mainOption;
    }
}