
package com.springboot.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.assignment.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
