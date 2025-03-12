package com.example.basicnewfeed.user.service;

import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.user.dto.UserRequestDto;
import com.example.basicnewfeed.user.dto.UserResponseDto;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void update(AuthUser authUser, UserRequestDto request) {

        // 유저 정보 확인
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        // 닉네임 중복 확인
        if (userRepository.existsByNickName(request.getNickName())) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }


        user.update(request.getNickName());
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다")
        );
        return new UserResponseDto(user.getId(), user.getEmail(), user.getNickName());
    }
}
