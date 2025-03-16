package com.example.basicnewfeed.comment.repository;

import com.example.basicnewfeed.comment.entity.Comment;
import com.example.basicnewfeed.comment.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    boolean existsByCommentAndUserId(Comment comment, Long id);
    void deleteByCommentAndUserId(Comment comment, Long userId);
    Optional<LikeComment> findByCommentAndUserId(Comment comment, Long UserId);
}
