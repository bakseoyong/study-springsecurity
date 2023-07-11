package com.example.login.Event;

import lombok.Getter;

@Getter
public class SendSignUpSuccessEvent {
    private String id;
    private String email;

    public SendSignUpSuccessEvent(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
