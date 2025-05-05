package com.practice.Assignment.controller;

import com.practice.Assignment.entities.store.User;
import com.practice.Assignment.entities.store.UserPassword;
import com.practice.Assignment.repo.store.UserPasswordRepository;
import com.practice.Assignment.repo.store.UserRepository;
import com.practice.Assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private UserPasswordRepository userPasswordRepository;

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

//    @PostMapping("/login")
//    public String login(@RequestParam int id, @RequestParam String password) {
//        User original = userRepository.findById(id).orElseThrow(() -> new NoSuchEmployeeExistsException("NO USER PRESENT WITH ID = " + id));
//
//        boolean matches = BCrypt.checkpw(password, original.getPassword());
//        if(matches){
//            return "password matches";
//        }else{
//            return "password didnot match";
//        }
//    }


    @GetMapping("/user-password")
    public List<UserPassword> allPasswords(){
        return userPasswordRepository.findAll();
    }

    @PostMapping("/save-password")
    public UserPassword save(@RequestBody UserPassword userPassword){
        return userPasswordRepository.save(userPassword);
    }

}
