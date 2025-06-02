package com.springboot.assignment.service;

import com.springboot.assignment.entity.User;
import com.springboot.assignment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }
    public String verify(User user) {
//        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken()  ;
//        } else {
//            return "fail";
//        }
        return "welcome";
    }
}

