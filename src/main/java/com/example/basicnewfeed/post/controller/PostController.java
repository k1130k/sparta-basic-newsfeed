package com.example.basicnewfeed.post.controller;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.post.dto.*;
import com.example.basicnewfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostSaveResponseDto> save(
            @Auth AuthUser authUser,
            @Valid @RequestBody PostSaveRequestDto dto
    ) {
        return ResponseEntity.ok(postService.save(authUser, dto));
    }

    @GetMapping("/api/v1/posts")
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/api/v1/posts/{postId}")
    public PostResponseDto findOne(
            @Auth AuthUser authUser,
            @Valid @PathVariable Long postId
    ) {
        return postService.findOne(authUser, postId);
    }

    @PatchMapping("/api/v1/posts/{postId}")
    public ResponseEntity<PostUpdateResponseDto> update(
            @Auth AuthUser authUser,
            @Valid @RequestBody PostUpdateRequestDto dto,
            @PathVariable Long postId
            ) {
        return ResponseEntity.ok(postService.update(authUser, dto, postId));
    }

    @DeleteMapping("/api/v1/posts/{postId}")
    public void delete (
            @Auth AuthUser authUser,
            @Valid @PathVariable Long postId
    ) {
        postService.deleteById(authUser, postId);
    }
}
