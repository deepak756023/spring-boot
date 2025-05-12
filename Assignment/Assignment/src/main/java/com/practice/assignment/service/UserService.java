package com.practice.assignment.service;

import com.practice.assignment.entities.store.User;
import com.practice.assignment.entities.store.UserPassword;
import com.practice.assignment.exception.custom_exception.EmailAlreadyUsedException;
import com.practice.assignment.exception.custom_exception.InvalidEmailException;
import com.practice.assignment.exception.custom_exception.NoSuchEmployeeExistsException;
import com.practice.assignment.exception.custom_exception.WrongPasswordException;
import com.practice.assignment.repo.store.UserPasswordRepository;
import com.practice.assignment.repo.store.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;

    public UserService(UserRepository userRepository, UserPasswordRepository userPasswordRepository) {
        this.userRepository = userRepository;
        this.userPasswordRepository = userPasswordRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        String mail = user.getMailId();
        String rawPassword = user.getPassword();

        if (!isValidEmail(mail)) {
            throw new InvalidEmailException("Email invalid!!!");
        }

        if (isEmailAlreadyUsed(mail)) {
            throw new EmailAlreadyUsedException("Email Already Used");
        }

        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        user.setPassword(hashedPassword);
        user.setCreatedOn(LocalDateTime.now());

        // Create and associate UserPassword
        UserPassword userPassword = new UserPassword();
        userPassword.setPasswordOne(hashedPassword);
        userPassword.setLastUpdated(LocalDateTime.now());
        userPassword.setUser(user);
        user.setUserPassword(userPassword); // Bidirectional link

        userRepository.save(user);
    }

    public User updateUser(int id, User user) {
        User original = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEmployeeExistsException("NO USER PRESENT WITH ID = " + id));

        original.setFirstName(user.getFirstName());
        original.setLastName(user.getLastName());
        original.setMailId(user.getMailId());
        original.setUpdatedBy(user.getUpdatedBy());
        original.setUpdatedOn(LocalDateTime.now());

        return userRepository.save(original);
    }

    public void changePwd(String mail, String oldPWD, String newPWD) {
        User user = userRepository.findByMailId(mail);

        if (user == null) {
            throw new InvalidEmailException("User with this email id doesn't exist");
        }


        UserPassword userPassword = userPasswordRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException("No password history found for user ID: " + user.getId()));


        if (BCrypt.checkpw(oldPWD, user.getPassword())) {

            if (matchesAnyPreviousPassword(newPWD, userPassword)) {
                throw new WrongPasswordException("Your new password cannot be one of your last three passwords");
            }

            String hashedPassword = BCrypt.hashpw(newPWD, BCrypt.gensalt());
            user.setPassword(hashedPassword);
            user.setUpdatedBy(user.getUpdatedBy());
            user.setUpdatedOn(LocalDateTime.now());
            userRepository.save(user);

            // Update password history
            userPassword.setPasswordThree(userPassword.getPasswordTwo());
            userPassword.setPasswordTwo(userPassword.getPasswordOne());
            userPassword.setPasswordOne(hashedPassword);
            userPassword.setLastUpdated(LocalDateTime.now());
            userPasswordRepository.save(userPassword);


        } else {
            throw new WrongPasswordException("Wrong Password!!!");
        }
    }

    private boolean matchesAnyPreviousPassword(String newPwd, UserPassword userPassword) {
        return (userPassword.getPasswordOne() != null && BCrypt.checkpw(newPwd, userPassword.getPasswordOne()))
                || (userPassword.getPasswordTwo() != null && BCrypt.checkpw(newPwd, userPassword.getPasswordTwo()))
                || (userPassword.getPasswordThree() != null && BCrypt.checkpw(newPwd, userPassword.getPasswordThree()));
    }

    private boolean isValidEmail(String mail) {
        return EmailValidator.getInstance().isValid(mail);
    }

    private boolean isEmailAlreadyUsed(String mail) {
        return userRepository.findByMailId(mail) != null;
    }

    public List<UserPassword> getPassword() {
        return userPasswordRepository.findAll();
    }

    public Boolean isUserActive(String mail) {
        User user = userRepository.findByMailId(mail);
        return user.getActive();
    }
}
