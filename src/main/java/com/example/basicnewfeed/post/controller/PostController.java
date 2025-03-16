package com.example.basicnewfeed.post.controller;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.post.dto.*;
import com.example.basicnewfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostSaveResponseDto> save(
            @Auth AuthUser authUser,
            @Valid @RequestBody PostSaveRequestDto dto) {
        return ResponseEntity.ok(postService.save(authUser, dto));
    }

    // 게시글 전체조회
    @GetMapping("/api/v1/posts")
    public ResponseEntity<Page<PostResponseDto>> findAll(
            @Auth AuthUser authUser,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(authUser, pageable));
    }

    // 게시글 단건조회
    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity<PostResponseDto> findOne(
            @Auth AuthUser authUser,
            @PathVariable Long postId) {
        return ResponseEntity.ok(postService.findOne(authUser, postId));
    }

    // 게시글 조건조회
    @GetMapping("/api/v1/posts/search")
    public ResponseEntity<Page<PostResponseDto>> search(
            @Auth AuthUser authUser,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(postService.search(authUser, startDate, endDate, sortBy, pageable));
    }

    // 팔로잉한사람 게시글 보기

    @GetMapping("/api/v1/posts/following")
    public ResponseEntity<Page<PostResponseDto>> getFollowingPosts(
            @Auth AuthUser authUser,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(postService.getFollowingPosts(authUser, pageable));
    }


    // 게시글 수정
    @PatchMapping("/api/v1/posts/{postId}")
    public ResponseEntity<PostUpdateResponseDto> update(
            @Auth AuthUser authUser,
            @Valid @RequestBody PostUpdateRequestDto dto,
            @PathVariable Long postId) {
        return ResponseEntity.ok(postService.update(authUser, dto, postId));
    }

    // 게시글 삭제
    @DeleteMapping("/api/v1/posts/{postId}")
    public void delete (
            @Auth AuthUser authUser,
            @Valid @PathVariable Long postId) {
        postService.deleteById(authUser, postId);
    }
}
