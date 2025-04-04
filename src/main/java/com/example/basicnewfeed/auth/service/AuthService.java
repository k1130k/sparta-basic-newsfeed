package com.example.basicnewfeed.auth.service;

import com.example.basicnewfeed.auth.dto.AuthLoginRequestDto;
import com.example.basicnewfeed.auth.dto.AuthSignupRequestDto;
import com.example.basicnewfeed.auth.dto.AuthLoginResponseDto;
import com.example.basicnewfeed.common.config.JwtUtil;
import com.example.basicnewfeed.common.config.PasswordEncoder;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(AuthSignupRequestDto request) {

        // 이메일이 중복되는 경우
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 가입된 이메일입니다");
        }

        // 닉네임 중복되는 경우
        if (userRepository.existsByNickName(request.getNickName())) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 가입 가능한 이메일인 경우
        User user = new User(
                request.getEmail(),
                encodedPassword,
                request.getNickName()
        );
        User savedUser = userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthLoginResponseDto login(AuthLoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        String bearerJwt = jwtUtil.createToken(user.getId(), user.getEmail());
        return new AuthLoginResponseDto(bearerJwt);
    }
}
