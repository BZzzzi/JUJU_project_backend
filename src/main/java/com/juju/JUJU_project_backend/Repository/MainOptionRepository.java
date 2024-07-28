package com.juju.JUJU_project_backend.Repository;

import com.juju.JUJU_project_backend.Entity.MainOption;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MainOptionRepository extends JpaRepository<MainOption, String>{
    Optional<MainOption> findByEmail(String email);
}
