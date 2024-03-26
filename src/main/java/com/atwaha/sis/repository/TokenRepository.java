package com.atwaha.sis.repository;

import com.atwaha.sis.model.entities.Token;
import com.atwaha.sis.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUserAndExpiredFalseOrRevokedFalse(User user);

    boolean existsByTokenAndExpiredFalseAndRevokedFalse(String token);

    Token findByToken(String token);
}