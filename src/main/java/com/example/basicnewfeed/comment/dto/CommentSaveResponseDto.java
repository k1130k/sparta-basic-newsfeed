package com.example.basicnewfeed.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentSaveResponseDto {

    private final Long id;
    private final String nickName;
    private final String comment;
    private final int likeComment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentSaveResponseDto(Long id,
                                  String nickName,
                                  String comment,
                                  int likeComment,
                                  LocalDateTime createdAt,
                                  LocalDateTime updatedAt
    ) {
        this.id = id;
        this.nickName = nickName;
        this.comment = comment;
        this.likeComment = likeComment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
