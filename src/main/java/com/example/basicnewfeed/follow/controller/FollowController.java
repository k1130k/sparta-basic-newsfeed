package com.example.basicnewfeed.follow.controller;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.follow.service.FollowService;
import com.example.basicnewfeed.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/v1/addfollow/{targetUserId}")
    public void following(
            @Auth AuthUser authUser,
            @PathVariable Long targetUserId) {
        followService.follow(authUser, targetUserId);
    }

    @DeleteMapping("/api/v1/addfollow/{targetUserId}")
    public void unFollow(
            @Auth AuthUser authUser,
            @PathVariable Long targetUserId) {
        followService.unFollow(authUser, targetUserId);

    }
}
