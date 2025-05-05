package com.practice.Assignment.repo.store;

import com.practice.Assignment.entities.store.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPassword, Integer> {
}
