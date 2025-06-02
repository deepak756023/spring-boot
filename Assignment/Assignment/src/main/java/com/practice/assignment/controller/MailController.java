package com.practice.assignment.controller;

import com.practice.assignment.helper.EmailSenderService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final EmailSenderService emailSenderService;

    public MailController(EmailSenderService emailSenderService){
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send-mail")
    public String sendMailWithAttachment(@RequestParam("toEmail") String toEmail,
                                         @RequestParam("subject") String subject,
                                         @RequestParam("body") String body,
                                         @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            emailSenderService.sendEmailWithOptionalAttachment(toEmail, subject, body, file);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}
