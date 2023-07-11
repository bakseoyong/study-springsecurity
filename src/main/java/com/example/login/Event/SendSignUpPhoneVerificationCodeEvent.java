package com.example.login.Event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendSignUpPhoneVerificationCodeEvent {
    private String phone;

    public SendSignUpPhoneVerificationCodeEvent(String phone) {
        this.phone = phone;
    }
}
