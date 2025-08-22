package com.practice.assignment.service;

import ch.qos.logback.core.util.StringUtil;
import com.practice.assignment.entities.store.PasswordResetToken;
import com.practice.assignment.entities.store.User;
import com.practice.assignment.entities.store.UserPassword;
import com.practice.assignment.exception.custom_exception.EmailAlreadyUsedException;
import com.practice.assignment.exception.custom_exception.InvalidEmailException;
import com.practice.assignment.exception.custom_exception.NoSuchEmployeeExistsException;
import com.practice.assignment.exception.custom_exception.WrongPasswordException;
import com.practice.assignment.helper.EmailSenderService;
import com.practice.assignment.repo.store.PasswordResetTokenRepository;
import com.practice.assignment.repo.store.UserPasswordRepository;
import com.practice.assignment.repo.store.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
//import java.util.logging.Logger;

@Service
@Transactional
public class UserService {

//    private static final Logger log = (Logger) LoggerFactory.getLogger(UserService.class);
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;
//    private
//    PasswordEncoder passwordEncoder;

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
        //user.setCreatedOn(LocalDateTime.now());

        // Create and associate UserPassword
//        UserPassword userPassword = new UserPassword();
//        userPassword.setPasswordOne(hashedPassword);
//        userPassword.setLastUpdated(LocalDateTime.now());
//        userPassword.setUser(user);
//        user.setUserPassword(userPassword); // Bidirectional link

        userRepository.save(user);
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

    public void changePwd(String mail, String oldPWD, String newPWD) {
        User user = userRepository.findByMailId(mail);

        if (user == null) {
            throw new InvalidEmailException("User with this email id doesn't exist");
        }


        UserPassword userPassword = userPasswordRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("No password history found for user ID: " + user.getId()));


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
        return (userPassword.getPasswordOne() != null && BCrypt.checkpw(newPwd, userPassword.getPasswordOne())) || (userPassword.getPasswordTwo() != null && BCrypt.checkpw(newPwd, userPassword.getPasswordTwo())) || (userPassword.getPasswordThree() != null && BCrypt.checkpw(newPwd, userPassword.getPasswordThree()));
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

    public Boolean isUserValid(String mail, String pwd) {
        User user = userRepository.findByMailId(mail);
        if (ObjectUtils.isEmpty(user)) return false;


        return Objects.equals(user.getMailId(), mail) && BCrypt.checkpw(pwd, user.getPassword());
    }

    //    for password reset
    public void createPasswordResetToken(String email) {
        User user = userRepository.findByMailId(email);

        if (user == null) {
            throw new NoSuchEmployeeExistsException("NO USER PRESENT WITH THIS EMAIL = " + email);
        }

        // Check if a token already exists for this user
        PasswordResetToken existingToken = tokenRepository.findByUser(user);

        String newToken = UUID.randomUUID().toString();
        LocalDateTime newExpiry = LocalDateTime.now().plusMinutes(15);

        if (existingToken != null) {
            // Always update the existing token with a new one
            existingToken.setToken(newToken);
            existingToken.setExpiryDate(newExpiry);
            tokenRepository.save(existingToken);
        } else {
            // Create a new token if none exists
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(newToken);
            resetToken.setUser(user);
            resetToken.setExpiryDate(newExpiry);
            tokenRepository.save(resetToken);
        }

        // Send the email with the new token
        emailSenderService.sendResetEmail(user.getMailId(), newToken);
    }


    public void resetPassword(String token, String newPassword) {
        if (StringUtil.isNullOrEmpty(token) || StringUtil.isNullOrEmpty(newPassword)) {
//            log.info("Either Token or newPassword is Empty");
            System.out.println("Either Token or newPassword is Empty");
        } else {
            PasswordResetToken resetToken = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));

            if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Token expired");
            }

            User user = resetToken.getUser();
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            userRepository.save(user);
//            tokenRepository.delete(resetToken);
        }
    }
}
