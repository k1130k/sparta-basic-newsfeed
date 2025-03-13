package com.example.basicnewfeed.auth.controller;

import com.example.basicnewfeed.auth.dto.AuthLoginRequestDto;
import com.example.basicnewfeed.auth.dto.AuthSignupRequestDto;
import com.example.basicnewfeed.auth.dto.AuthLoginResponseDto;
import com.example.basicnewfeed.auth.service.AuthService;
import jakarta.validation.Valid;
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
    public void signup(@Valid @RequestBody AuthSignupRequestDto request) {
        authService.signup(request);
    }

    @PostMapping("/api/v1/auth/users/login")
    public ResponseEntity<AuthLoginResponseDto> login(@Valid @RequestBody AuthLoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
