package com.example.basicnewfeed.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostSaveResponseDto {
    private final Long id;
    private final String content;
    private final Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostSaveResponseDto(Long id, String content, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
