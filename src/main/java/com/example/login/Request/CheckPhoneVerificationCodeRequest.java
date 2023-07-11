package com.example.login.Request;

import lombok.Getter;

@Getter
public class CheckPhoneVerificationCodeRequest {
    private String phone;
    private String code;

    public CheckPhoneVerificationCodeRequest(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }
}