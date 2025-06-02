package com.practice.assignment.helper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithOptionalAttachment(String toEmail, String subject, String body, MultipartFile file) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("deepaknayak55260@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);

        if (file != null && !file.isEmpty()) {
            helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), new ByteArrayResource(file.getBytes()));
        }

        mailSender.send(message);
    }

}
