package com.example.basicnewfeed.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long id;
    private String NickName;
    private String content;
    private int likePost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(Long id, String NickName, String content, int likePost, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.NickName = NickName;
        this.content = content;
        this.likePost = likePost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
