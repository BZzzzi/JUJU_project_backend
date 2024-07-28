package com.juju.JUJU_project_backend.Repository;

import com.juju.JUJU_project_backend.Entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<Events, Integer> {
}
