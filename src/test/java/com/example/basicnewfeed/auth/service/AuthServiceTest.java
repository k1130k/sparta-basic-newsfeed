package com.example.basicnewfeed.auth.service;

import com.example.basicnewfeed.auth.dto.AuthSignupRequestDto;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;

    @Test
    void 유저를_저장할_수_있다() {
        //given
        String email = "test@naver.com";
        String password = "qweQ12!";
        String NickName = "김정은";
        AuthSignupRequestDto request = new AuthSignupRequestDto(email, password, NickName);
        User user = new User(email, password, NickName);
        ReflectionTestUtils.setField(user, "id", 1L);
        given(userRepository.save(any(User.class))).willReturn(user);

        //when
       authService.signup(request);

        //then
        assertNotNull();
        assertThat();
    }
}