package com.example.basicnewfeed.auth.service;

import com.example.basicnewfeed.auth.dto.AuthRequestDto;
import com.example.basicnewfeed.auth.dto.AuthResponseDto;
import com.example.basicnewfeed.common.config.JwtUtil;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(AuthRequestDto request) {

        // 이메일이 중복되는 경우
        if (userRepository.existByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 가입된 이메일입니다");
        }

        // 가입 가능한 이메일인 경우
        User user = new User(request.getEmail(), request.getPassword(), request.getNickname());
        User savedUser = userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthResponseDto login(AuthRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );

        String password = request.getPassword();
        if(!password.equals(user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        String bearerJwt = jwtUtil.createToken(user.getId(), user.getEmail());
        return new AuthResponseDto(bearerJwt);


    }
}
