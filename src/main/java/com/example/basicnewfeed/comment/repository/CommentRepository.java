package com.example.basicnewfeed.comment.repository;

import com.example.basicnewfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
