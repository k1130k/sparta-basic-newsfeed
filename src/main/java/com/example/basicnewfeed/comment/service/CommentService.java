package com.example.basicnewfeed.comment.service;

import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.comment.dto.*;
import com.example.basicnewfeed.comment.entity.Comment;
import com.example.basicnewfeed.comment.repository.CommentRepository;
import com.example.basicnewfeed.post.entity.Post;
import com.example.basicnewfeed.post.repository.PostRepository;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 작성
    @Transactional
    public CommentSaveResponseDto save(AuthUser authUser, CommentSaveRequestDto dto, Long postId) {
        Post post = postRepository.findById(postId). orElseThrow(
                () -> new IllegalStateException("해당 게시물이 존재하지 않습니다.")
        );

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("사용자가 존재하지 않습니다.")
        );
        Comment comment = new Comment(dto.getComment(), post, user);
        Comment savedComment = commentRepository.save(comment);
        return new CommentSaveResponseDto(
                savedComment.getId(),
                comment.getUser().getNickName(),
                savedComment.getComment(),
                savedComment.getLikeComment(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
        );
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(AuthUser authUser, Long postId) {
        postRepository.findById(postId). orElseThrow(
                () -> new IllegalStateException("해당 게시물이 존재하지 않습니다.")
        );

        List<Comment> comments = commentRepository.findAll();
        List<CommentResponseDto> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            dtos.add(new CommentResponseDto(
                    comment.getId(),
                    comment.getUser().getNickName(),
                    comment.getComment(),
                    comment.getLikeComment(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt()
            ));
        }
        return dtos;
    }

    // 댓글수정
    @Transactional
    public CommentUpdateResponseDto update(AuthUser authUser, CommentUpdateRequestDto dto, Long postId, Long commentId) {

        postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 게시물이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("해당 댓글이 존재하지 않습니다.")
        );

        if(!authUser.getId().equals(comment.getUser().getId())) {
            throw new IllegalStateException("본인만 수정할 수 있습니다.");
        }

        comment.update(dto.getComment());
        return new CommentUpdateResponseDto(
                comment.getId(),
                comment.getUser().getNickName(),
                comment.getComment(),
                comment.getLikeComment(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    public void deleteById(AuthUser authUser, Long postId, Long commentId) {

        postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 게시물이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("해당 댓글이 존재하지 않습니다.")
        );
        if(!authUser.getId().equals(comment.getUser().getId())) {
            throw new IllegalStateException("본인만 삭제할 수 있습니다.");
        }

        commentRepository.deleteById(commentId);
    }
}
