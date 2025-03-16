package com.example.basicnewfeed.comment.controller;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.comment.dto.*;
import com.example.basicnewfeed.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<CommentSaveResponseDto> save(@Auth AuthUser authUser,
                                                      @Valid @RequestBody CommentSaveRequestDto dto,
                                                      @PathVariable Long postId) {
        return ResponseEntity.ok(commentService.save(authUser, dto, postId));
    }

    // 댓글 조회
    @GetMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findAll(@Auth AuthUser authUser,
                                            @PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findAll(authUser, postId));
    }

    // 댓글 수정
    @PatchMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> update(@Auth AuthUser authUser,
                                           @Valid @RequestBody CommentUpdateRequestDto dto,
                                           @PathVariable Long postId,
                                           @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.update(authUser, dto, postId, commentId));
    }

    // 댓글 삭제
    @DeleteMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public void delete(@Auth AuthUser authUser,
                       @PathVariable Long postId,
                       @PathVariable Long commentId) {
        commentService.deleteById(authUser, postId, commentId);
    }
}
