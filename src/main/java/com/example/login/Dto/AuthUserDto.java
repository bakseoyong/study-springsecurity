package com.example.login.Dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthUserDto {
    private String id;
    private String message;

    @Builder
    public AuthUserDto(String id, String message) {
        this.id = id;
        this.message = message;
    }
}
