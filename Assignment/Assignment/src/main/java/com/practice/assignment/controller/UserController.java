package com.practice.assignment.controller;

import com.practice.assignment.entities.store.User;
import com.practice.assignment.entities.store.UserPassword;
import com.practice.assignment.repo.store.UserPasswordRepository;
import com.practice.assignment.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    private final UserPasswordRepository userPasswordRepository;

    public UserController(UserPasswordRepository userPasswordRepository, UserService userService) {
        this.userPasswordRepository = userPasswordRepository;
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping("/save-user")
    public void addUser(@RequestBody User user){
         this.userService.saveUser(user);
    }

    @PutMapping("/update-user")
    public User updateUser(@RequestParam int id, @RequestBody User user){
        return userService.updateUser(id, user);

    }


    @GetMapping("/user-password")
    public List<UserPassword> allPasswords(){
        return userPasswordRepository.findAll();
    }

    @PostMapping("/save-password")
    public UserPassword save(@RequestBody UserPassword userPassword){
        return userPasswordRepository.save(userPassword);
    }

}
