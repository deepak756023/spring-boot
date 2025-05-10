package com.practice.assignment.service;

import com.practice.assignment.exception.custom_exception.EmailAlreadyUsedException;
import com.practice.assignment.exception.custom_exception.InvalidEmailException;
import com.practice.assignment.exception.custom_exception.WrongPasswordException;
import org.apache.commons.validator.routines.EmailValidator;
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
        String mail = user.getMailId();
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        if(isValidEmail(mail)){
            if(!isEmailAlreadyUsed(mail)) {
                user.setCreatedOn(LocalDateTime.now());
                this.userRepository.save(user);
            }else{
                throw new EmailAlreadyUsedException("Email Already Used");
            }
        }else{
            throw new InvalidEmailException("Email invalid!!!");
        }


    }

    public User updateUser(int id, User user) {
        User original = userRepository.findById(id).orElseThrow(() -> new NoSuchEmployeeExistsException("NO USER PRESENT WITH ID = " + id));
        original.setFirstName(user.getFirstName());
        original.setLastName(user.getLastName());
        original.setMailId(user.getMailId());
        original.setUpdatedBy(user.getUpdatedBy());
        original.setUpdatedOn(LocalDateTime.now());
        return userRepository.save(original);


    }

    private boolean isValidEmail(String mail){
        return EmailValidator.getInstance().isValid(mail);
    }

    private boolean isEmailAlreadyUsed(String mail) {
        List<User> list = getAll();
        return list.stream()
                .map(User::getMailId)
                .anyMatch(e -> e.equals(mail));
    }


    public void changePwd(String mail, String oldPWD, String newPWD) {

        User user = userRepository.findByMailId(mail);
        if(user == null){
            throw new InvalidEmailException("Wrong Email Id!!!");

        } else if (BCrypt.checkpw(oldPWD, user.getPassword())) {
            user.setPassword(BCrypt.hashpw(newPWD, BCrypt.gensalt()));
            user.setUpdatedBy(user.getUpdatedBy());
            user.setUpdatedOn(LocalDateTime.now());
            this.userRepository.save(user);
        } else {
            throw new WrongPasswordException("Wrong Password!!!");
        }

    }
}
