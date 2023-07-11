package com.example.login.Exception;

import lombok.Getter;

@Getter
public class EmailSendFailException extends BusinessException{
    String email;

    public EmailSendFailException(String message, String email) {
        super(message);
        this.email = email;
    }
}
