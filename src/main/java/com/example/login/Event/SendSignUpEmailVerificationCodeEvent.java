package com.example.login.Event;

import lombok.Getter;

@Getter
public class SendSignUpEmailVerificationCodeEvent {
    private String email;

    public SendSignUpEmailVerificationCodeEvent(String email) {
        this.email = email;
    }
}
