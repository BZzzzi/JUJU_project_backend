package com.juju.JUJU_project_backend.Repository;

import com.juju.JUJU_project_backend.Entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventsRepository extends JpaRepository<Events, String> {
    List<Events> findByEmail(String email);
    Optional<Events> findByEmailAndTitle(String email, String title);
}
