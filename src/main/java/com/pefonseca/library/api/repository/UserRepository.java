package com.pefonseca.library.api.repository;

import com.pefonseca.library.api.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findByLogin(String login);

    AuthUser findByEmail(String email);
}
