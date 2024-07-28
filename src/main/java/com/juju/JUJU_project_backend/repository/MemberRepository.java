package com.juju.JUJU_project_backend.repository;

import com.juju.JUJU_project_backend.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
