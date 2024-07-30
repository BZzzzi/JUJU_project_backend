package com.juju.JUJU_project_backend.Repository;

import com.juju.JUJU_project_backend.Entity.MainOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainOptionRepository extends JpaRepository<MainOption, String>{
    Optional<MainOption> findByEmailIgnoreCase(String email);
}
