package com.example.basicnewfeed.auth.controller;

import com.example.basicnewfeed.auth.dto.AuthRequestDto;
import com.example.basicnewfeed.auth.dto.AuthResponseDto;
import com.example.basicnewfeed.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/auth/users/signup")
    public void signup(@RequestBody AuthRequestDto request) {
        authService.signup(request);
    }

    @PostMapping("/api/v1/auth/users/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
