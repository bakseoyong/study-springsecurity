package com.example.login.Request;

import lombok.Getter;

@Getter
public class CheckEmailVerificationCodeRequest {
    private String email;
    private String code;

    public CheckEmailVerificationCodeRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
