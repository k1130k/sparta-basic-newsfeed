package com.example.basicnewfeed.comment.controller;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.comment.service.LikeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeCommentController {

    private final LikeCommentService likeCommentService;

    // 댓글 좋아요
    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}/likes")
    public void likeComment(@Auth AuthUser authUser,
                            @PathVariable Long postId,
                            @PathVariable Long commentId) {
        likeCommentService.likeComment(authUser, postId, commentId);
    }

    // 댓글 좋아요 취소
    @DeleteMapping("/api/v1/posts/{postId}/comments/{commentId}/likes")
        public void delete(@Auth AuthUser authUser,
                           @PathVariable Long postId,
                           @PathVariable Long commentId) {
        likeCommentService.delete(authUser, postId, commentId);
    }
}
