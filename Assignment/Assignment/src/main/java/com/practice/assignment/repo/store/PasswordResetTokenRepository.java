package com.practice.assignment.repo.store;

import com.practice.assignment.entities.store.PasswordResetToken;
import com.practice.assignment.entities.store.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken findByUser(User user);
}
