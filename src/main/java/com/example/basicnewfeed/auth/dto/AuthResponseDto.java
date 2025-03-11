package com.example.basicnewfeed.auth.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {

    private final String bearerJwt;

    public AuthResponseDto(String bearerJwt) {
        this.bearerJwt = bearerJwt;
    }
}
