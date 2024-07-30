package com.juju.JUJU_project_backend.Repository;

import com.juju.JUJU_project_backend.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByVerificationToken(String verificationToken);
    Optional<Token> findByEmail(String email);
}
