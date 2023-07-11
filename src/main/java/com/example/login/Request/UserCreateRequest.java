package com.example.login.Request;

import lombok.Getter;

@Getter
public class UserCreateRequest {
    private String id;
    private String email;
    private String password;

    public UserCreateRequest(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
