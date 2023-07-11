package com.example.login.EventHandler;

import com.example.login.EmailService;
import com.example.login.Event.SendSignUpEmailVerificationCodeEvent;
import com.example.login.Event.SendSignUpSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailEventHandler {
    EmailService emailService;

    @Autowired
    public EmailEventHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleSendSignUpEmailVerificationCodeEvent(SendSignUpEmailVerificationCodeEvent event){
        String email = event.getEmail();

        emailService.sendVerificationCode(email);
    }

    @EventListener
    public void handleSendSignUpSuccessEvent(SendSignUpSuccessEvent event){
        String id = event.getId();
        String email = event.getEmail();

        emailService.sendWelcomeMessage(id, email);
    }
}
