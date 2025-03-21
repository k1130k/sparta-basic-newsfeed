package com.example.basicnewfeed.follow.service;

import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.follow.entity.Follow;
import com.example.basicnewfeed.follow.repository.FollowRepository;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
    @InjectMocks
    private FollowService followService;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void 팔로우를_한다() {
        // given
        Long followingId = 1L;
        Long followerId = 3L;
        String followingEmail = "test@test.com";
        String followerEmail = "test3@test.com";

        AuthUser authUser = new AuthUser(followingId, followingEmail);

        User following = new User(followingEmail, "!Qw1234567", "김정은");
        User follower = new User(followerEmail, "!Qw1234567", "김주애");
        ReflectionTestUtils.setField(following, "id", followingId);
        ReflectionTestUtils.setField(follower, "id", followerId);

        Follow follow = new Follow(following, follower);
        ReflectionTestUtils.setField(follow, "id", 1L);

        given(userRepository.findById(followingId)).willReturn(Optional.of(following));
        given(userRepository.findById(followerId)).willReturn(Optional.of(follower));
        given(followRepository.existsByFollowingAndFollower(following, follower)).willReturn(false);
        given(followRepository.save(any(Follow.class))).willReturn(follow);

        // when
        followService.follow(authUser, followerId);

        // then
        assertThat(following.getFollowingCount()).isEqualTo(1);  // 내가 팔로우하는 사람 수 증가
        assertThat(follower.getFollowerCount()).isEqualTo(1);    // 상대방의 팔로워 수 증가

        // Follow 엔티티 관계 검증
        ArgumentCaptor<Follow> followCaptor = ArgumentCaptor.forClass(Follow.class);
        verify(followRepository).save(followCaptor.capture());
        Follow savedFollow = followCaptor.getValue();
        assertThat(savedFollow.getFollowing()).isEqualTo(following);
        assertThat(savedFollow.getFollower()).isEqualTo(follower);
    }
}