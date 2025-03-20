package com.example.basicnewfeed.auth.service;

import com.example.basicnewfeed.auth.dto.AuthLoginRequestDto;
import com.example.basicnewfeed.auth.dto.AuthLoginResponseDto;
import com.example.basicnewfeed.auth.dto.AuthSignupRequestDto;
import com.example.basicnewfeed.common.config.JwtUtil;
import com.example.basicnewfeed.common.config.PasswordEncoder;
import com.example.basicnewfeed.user.dto.UserResponseDto;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import com.example.basicnewfeed.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
     private JwtUtil jwtUtil;

    @Test
    void 회원가입을_한다() {
        //given
        String email = "test@naver.com";
        String password = "qweQ12!";
        String nickName = "김정은";
        AuthSignupRequestDto request = new AuthSignupRequestDto(email, password, nickName);
        String encodedPassword = "encodedPassword";

        given(userRepository.existsByEmail(email)).willReturn(false);
        given(userRepository.existsByNickName(nickName)).willReturn(false);
        given(passwordEncoder.encode(password)).willReturn(encodedPassword);

        User user = new User(email, password, nickName);
        ReflectionTestUtils.setField(user, "id", 1L);
        given(userRepository.save(any(User.class))).willReturn(user);

        //when
        authService.signup(request);

        //then
        verify(userRepository).existsByEmail(email);  // 이메일 중복 체크 호출 확인
        verify(userRepository).existsByNickName(nickName);  // 닉네임 중복 체크 호출 확인
        verify(passwordEncoder).encode(password);  // 비밀번호 인코딩 호출 확인
        verify(userRepository).save(any(User.class));  // save 메소드 호출 확인
    }

    @Test
    void 로그인을_한다() {
        // given
        String email = "test@naver.com";
        String password = "qweQ12!";
        String encodedPassword = "encodedPassword";
        Long userId = 1L;
        String token = "Bearer test.jwt.token";

        AuthLoginRequestDto request = new AuthLoginRequestDto(email,password);
        User user = new User(email, encodedPassword, "김정은");
        ReflectionTestUtils.setField(user, "id", userId);

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(password,encodedPassword)).willReturn(true);
        given(jwtUtil.createToken(userId, email)).willReturn(token);

        // when
        AuthLoginResponseDto response = authService.login(request);

        // then
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(password, encodedPassword);
        verify(jwtUtil).createToken(userId, email);
        assertThat(response.getBearerJwt()).isEqualTo(token);
    }

}