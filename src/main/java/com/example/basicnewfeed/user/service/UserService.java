package com.example.basicnewfeed.user.service;

import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.common.config.PasswordEncoder;
import com.example.basicnewfeed.user.dto.ChangePasswordRequestDto;
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
    private final PasswordEncoder passwordEncoder;

    //유저조회
    @Transactional(readOnly = true)
    public UserResponseDto findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다")
        );
        return new UserResponseDto(user.getId(), user.getEmail(), user.getNickName());
    }

    // 프로필 수정
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

    // 비밀번호 수정
    @Transactional
    public UserResponseDto changePassword(AuthUser authUser, ChangePasswordRequestDto request) {

        // 유저 정보 확인
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        // 비밀번호 일치 여부
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        // 기존 비밀번호와 변경비밀번호 비교
        if(passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalStateException("기존 비밀번호와 새 비밀번호는 같을 수 없습니다.");
        }

        // 업데이트
        user.changePassword(passwordEncoder.encode(request.getNewPassword()));

        return new UserResponseDto(
                user.getId(),
                user.getPassword(),
                user.getNickName()
        );
    }
}
