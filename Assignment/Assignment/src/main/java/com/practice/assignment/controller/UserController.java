package com.practice.assignment.controller;

import com.practice.assignment.entities.store.ResetPasswordRequest;
import com.practice.assignment.entities.store.User;
import com.practice.assignment.entities.store.UserPassword;
import com.practice.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/all-passwords")
    public List<UserPassword> getAllPassword(){
        return userService.getPassword();
    }

    @GetMapping("/user-isActive")
    public Boolean isActive(@RequestParam String mail){
        return userService.isUserActive(mail);
    }

    @GetMapping("/login")
    public Boolean isValidAuthentication(@RequestParam String mail, @RequestParam String pwd){
        return userService.isUserValid(mail, pwd);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        userService.createPasswordResetToken(email);
        return ResponseEntity.ok("Password reset link sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(true);
    }











}
