package com.example.login.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthRequest {
    private String id;
    private String password;

    public UserAuthRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
