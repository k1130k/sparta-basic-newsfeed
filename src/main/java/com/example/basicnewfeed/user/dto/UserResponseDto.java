package com.example.basicnewfeed.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String email;
    private final String nickName;
    private int followingCount;
    private int followerCount;

    public UserResponseDto(Long id, String email, String nickName, int followingCount, int followerCount) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }
}
