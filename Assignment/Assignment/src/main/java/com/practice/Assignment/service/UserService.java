package com.practice.Assignment.service;

import org.mindrot.jbcrypt.BCrypt;
import com.practice.Assignment.entities.store.User;
import com.practice.Assignment.exception.custom_exception.NoSuchEmployeeExistsException;
import com.practice.Assignment.repo.store.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public List<User> getAll() {
        return userRepository.findAll();
    }



    public void saveUser(User user) {
        user.setCreatedOn(LocalDateTime.now());
        this.userRepository.save(user);

    }

    public User updateUser(int id, User user) {
        User original = userRepository.findById(id).orElseThrow(() -> new NoSuchEmployeeExistsException("NO USER PRESENT WITH ID = " + id));
        original.setFirstName(user.getFirstName());
        original.setLastName(user.getLastName());
        original.setUpdatedBy(user.getUpdatedBy());
        original.setUpdatedOn(LocalDateTime.now());
        original.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(original);


    }


}
