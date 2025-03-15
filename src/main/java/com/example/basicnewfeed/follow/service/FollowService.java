package com.example.basicnewfeed.follow.service;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.follow.entity.Follow;
import com.example.basicnewfeed.follow.repository.FollowRepository;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    public final FollowRepository followRepository;
    public final UserRepository userRepository;

    @Transactional
    public void follow(AuthUser authUser, Long targetUserId) {

        User following = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("회원이 존재하지 않습니다.")
        );

        User follower = userRepository.findById(targetUserId).orElseThrow(
                () -> new IllegalStateException("회원이 존재하지 않습니다.")
        );

        if(authUser.getId().equals(targetUserId)) {
            throw new IllegalStateException("본인은 팔로우 할 수 없습니다.");
        }

        followRepository.save(new Follow(following, follower));

        following.increaseFollowingCount();
        follower.increaseFollowerCount();
    }

    public void unFollow(AuthUser authUser, Long userId) {
        User following = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("회원이 존재하지 않습니다.")
        );

        User follower = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("회원이 존재하지 않습니다.")
        );

        Follow follow = followRepository.findByFollowingAndFollower(following, follower).orElseThrow(
                () -> new IllegalStateException("팔로우 관계가 존재하지 않습니다.")
        );
      followRepository.delete(follow);

        following.decreaseFollowingCount();
        follower.decreaseFollowerCount();
    }
}
