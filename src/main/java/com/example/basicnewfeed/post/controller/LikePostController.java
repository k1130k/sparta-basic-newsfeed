package com.example.basicnewfeed.post.controller;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.post.service.LikePostService;
import com.example.basicnewfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikePostController {

    private final LikePostService likePostService;

    // 좋아요
    @PostMapping("/api/v1/posts/{postId}/likes")
    public void likePost(@Auth AuthUser authUser,
                         @PathVariable Long postId) {
        likePostService.likePost(authUser, postId);
    }

    // 좋아요 취소
    @DeleteMapping("/api/v1/posts/{postId}/likes")
    public void delete(@Auth AuthUser authUser,
                       @PathVariable Long postId) {
        likePostService.deleteById(authUser, postId);
    }


}
