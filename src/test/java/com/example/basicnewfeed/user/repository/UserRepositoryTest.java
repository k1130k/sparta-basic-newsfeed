package com.example.basicnewfeed.user.repository;

import com.example.basicnewfeed.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void 유저_전체를_가져올_수_있다() {
        // given
        String email = "test@naver.com";
        String password = "qweQ12!";
        String NickName = "김정은";
        User user = new User(email, password, NickName);
        userRepository.save(user);

        // when
        List<User> users = userRepository.findAll();

        // then
        assertNotNull(users);
        assertThat(users.size()).isEqualTo(1);
    }

}