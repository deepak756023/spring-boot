package com.practice.assignment.controller;

import com.practice.assignment.entities.store.User;
import com.practice.assignment.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
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

    @PutMapping("/password-change")
    public void changePassword(@RequestParam String mail, @RequestParam String oldPWD, @RequestParam String newPWD){
        this.userService.changePwd(mail, oldPWD, newPWD);
    }








}
