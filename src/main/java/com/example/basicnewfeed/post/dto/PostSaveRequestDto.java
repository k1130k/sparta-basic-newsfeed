package com.example.basicnewfeed.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostSaveRequestDto {

    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}
