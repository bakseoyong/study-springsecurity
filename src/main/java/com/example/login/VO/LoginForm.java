package com.example.login.VO;

import lombok.Getter;

@Getter
public class LoginForm {
    private String username;

    private String password;

    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
