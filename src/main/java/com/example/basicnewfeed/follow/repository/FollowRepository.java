package com.example.basicnewfeed.follow.repository;

import com.example.basicnewfeed.follow.entity.Follow;
import com.example.basicnewfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository <Follow, Long> {
    Optional<Follow> findByFollowingAndFollower(User following, User follower);
}
