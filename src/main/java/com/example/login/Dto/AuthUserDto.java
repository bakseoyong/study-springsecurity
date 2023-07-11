package com.example.login.Dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthUserDto {
    private String id;
    private boolean isSuccess;
    private String message;

    @Builder
    public AuthUserDto(String id, boolean isSuccess, String message) {
        this.id = id;
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
