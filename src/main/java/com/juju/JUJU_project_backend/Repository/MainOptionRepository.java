package com.juju.JUJU_project_backend.Repository;

import com.juju.JUJU_project_backend.Entity.MainOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainOptionRepository extends JpaRepository<MainOption,Integer> {
    MainOption findByemail_id(String email_id);
}
