package com.example.login.VO;

import lombok.Getter;

@Getter
public class SignUpForm {
    private String username;
    private String password;
    private String email;

    public SignUpForm(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
