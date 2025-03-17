package com.example.basicnewfeed.follow.repository;

import com.example.basicnewfeed.follow.entity.Follow;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 팔로우를_할_수_있다() {
        //given
        String email = "test@naver.com";
        String password = "qweQ12!";
        String nickName = "김정은";
        User following = new User(email, password, nickName);
        userRepository.save(following);

        String email2 = "test2@naver.com";
        String password2 = "qweQ12!";
        String nickName2 = "김주애";
        User follower = new User(email2, password2, nickName2);
        userRepository.save(follower);

        Follow follow = new Follow(following,follower);
        followRepository.save(follow);

        //when
        List<Follow> follows = followRepository.findAll();

        //then
        assertNotNull(follows);
        assertThat(follows.size()).isEqualTo(1);
    }
}