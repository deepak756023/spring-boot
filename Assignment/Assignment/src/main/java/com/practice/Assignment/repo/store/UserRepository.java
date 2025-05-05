package com.practice.Assignment.repo.store;

import com.practice.Assignment.entities.store.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
