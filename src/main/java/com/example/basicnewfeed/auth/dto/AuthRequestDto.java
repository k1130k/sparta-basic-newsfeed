package com.example.basicnewfeed.auth.dto;

import lombok.Getter;

@Getter
public class AuthRequestDto {

    private String email;
    private String password;
    private String nickname;
}
