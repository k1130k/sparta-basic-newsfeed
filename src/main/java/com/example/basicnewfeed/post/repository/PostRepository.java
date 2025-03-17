package com.example.basicnewfeed.post.repository;

import com.example.basicnewfeed.post.entity.Post;
import com.example.basicnewfeed.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByUserInOrderByCreatedAtDesc(List<User> users, Pageable pageable);

    Page<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
