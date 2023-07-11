package com.example.login.Dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.OptionalLong;

@Getter
public class AuthUserDto {
    @Nullable
    private Long id;
    private String message;

    @Builder
    public AuthUserDto(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}
