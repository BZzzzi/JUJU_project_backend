package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainOptionService {
    @Autowired
    private MainOptionRepository repository;

    public findByemail_id getUserProfile(String email_id) {
        return repository.findByemail_id(email_id);
    }

    public UserProfile updateUserProfile(String username, UserProfile newProfile) {
        UserProfile existingProfile = repository.findByUsername(username);
        if (existingProfile != null) {
            existingProfile.setEmail(newProfile.getEmail());
            existingProfile.setBio(newProfile.getBio());
            existingProfile.setProfilePicture(newProfile.getProfilePicture());
            return repository.save(existingProfile);
        } else {
            return null;
        }
    }
}
