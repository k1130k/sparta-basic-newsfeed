package com.example.basicnewfeed.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;

    public UserResponseDto(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
