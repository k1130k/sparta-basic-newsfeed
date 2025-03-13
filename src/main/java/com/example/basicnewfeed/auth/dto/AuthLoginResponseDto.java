package com.example.basicnewfeed.auth.dto;

import lombok.Getter;

@Getter
public class AuthLoginResponseDto {

    private final String bearerJwt;

    public AuthLoginResponseDto(String bearerJwt) {
        this.bearerJwt = bearerJwt;
    }
}
