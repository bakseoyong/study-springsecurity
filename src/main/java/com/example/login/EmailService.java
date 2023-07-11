package com.example.login;

import com.example.login.Exception.EmailSendFailException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RestTemplate restTemplate;

    public EmailService(JavaMailSender javaMailSender, RestTemplate restTemplate) {
        this.javaMailSender = javaMailSender;
        this.restTemplate = restTemplate;
    }

    public void sendVerificationCode(String email){
        String code = generateVerificationCode();
        String url = "http://localhost:8080/email/store/{key}/{value}";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("인증번호");
        message.setText(code);
        try {
            javaMailSender.send(message);
        }catch (MailException e){
            throw new EmailSendFailException(email, "Failed to send verification code email.");
        }

        String result =
                restTemplate.getForObject(url, String.class, email, code);

    }

    public void sendWelcomeMessage(String id, String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("환영합니다.");
        message.setText(id + "님. 회원가입 하셨습니다.");
        try {
            javaMailSender.send(message);
        }catch (MailException e){
            throw new EmailSendFailException(email, "Failed to send welcome message email.");
        }
    }


    private String generateVerificationCode() {
        return "0000";
    }
}
