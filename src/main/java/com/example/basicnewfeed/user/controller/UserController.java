package com.example.basicnewfeed.user.controller;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.user.dto.UserRequestDto;
import com.example.basicnewfeed.user.dto.UserResponseDto;
import com.example.basicnewfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //프로필 조회
    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<UserResponseDto> findOne(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    // 프로필 수정
    @PatchMapping("/api/v1/mypage")
        public void update(
                @Auth AuthUser authUser,
                @RequestBody UserRequestDto request) {
        userService.update(authUser, request);
        }

        // 비밀번호 수정
    @PatchMapping("/api/v1/mypage/password")
    public void changePassword(
            @Auth AuthUser authUser,
            @RequestBody
    )
}
