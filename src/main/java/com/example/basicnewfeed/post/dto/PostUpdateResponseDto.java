package com.example.basicnewfeed.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUpdateResponseDto {

    private final Long id;
    private final Long userId;
    private final String content;
    private final int likePost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostUpdateResponseDto(Long id, Long userId, String content, int likePost, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.likePost = likePost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
