package com.example.basicnewfeed.post.repository;

import com.example.basicnewfeed.post.entity.LikePost;
import com.example.basicnewfeed.post.entity.Post;
import com.example.basicnewfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    boolean existsByPostAndUserId(Post post, Long userId);
    void deleteByPostAndUserId(Post post, Long id);
}
