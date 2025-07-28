package com.pettrek.backend.auth.services;

import com.pettrek.backend.auth.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.tools.JavaFileManager;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${app.baseUrl}")
    private String baseUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Email Verification");
        message.setText("Здравуствуйте, это Pettrek, перейди по ссылке и подтверди регистрацию:\n"
                + baseUrl + "/api/auth/verify?code=" + user.getVerificationCode());

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, please click the link below:\n"
                + baseUrl + "/api/auth/reset-password?token=" + user.getResetPasswordToken());

        mailSender.send(message);
    }
}
