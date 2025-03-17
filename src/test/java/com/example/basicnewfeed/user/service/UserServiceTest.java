package com.example.basicnewfeed.user.service;

import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.awaitility.Awaitility.given;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void 유저를_저장할_수_있다() {
        //given
        String email = "test@naver.com";
        String password = "qweQ12!";
        String NickName = "김정은";
        User user = new User(email, password, NickName);

        given(userRepository.findById(any(User.class))).willReturn(user)
        //when

        //then
    }

}