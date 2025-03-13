package com.example.basicnewfeed.post.dto;

import lombok.Getter;

@Getter
public class PostSaveResponseDto {
    private final Long id;
    private final String content;
    private final Long userId;

    public PostSaveResponseDto(Long id, String content, Long userId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
    }
}
