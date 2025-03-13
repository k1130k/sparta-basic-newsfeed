package com.example.basicnewfeed.post.dto;

import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;
    private Long userId;
    private String content;
    private int likePost;

    public PostResponseDto(Long id, Long userId, String content, int likePost) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.likePost = likePost;
    }
}
