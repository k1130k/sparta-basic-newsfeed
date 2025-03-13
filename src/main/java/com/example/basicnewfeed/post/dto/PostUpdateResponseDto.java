package com.example.basicnewfeed.post.dto;

import lombok.Getter;

@Getter
public class PostUpdateResponseDto {

    private final Long id;
    private final Long userId;
    private final String content;
    private final int likePost;

    public PostUpdateResponseDto(Long id, Long userId, String content, int likePost) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.likePost = likePost;
    }
}
