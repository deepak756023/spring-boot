package com.springboot.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.springboot.assignment.entity.User;
import com.springboot.assignment.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    // 13
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    public String verify(User user) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "fail";

    }

}
