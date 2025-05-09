package com.practice.assignment.service;

import org.mindrot.jbcrypt.BCrypt;
import com.practice.assignment.entities.store.User;
import com.practice.assignment.exception.custom_exception.NoSuchEmployeeExistsException;
import com.practice.assignment.repo.store.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
