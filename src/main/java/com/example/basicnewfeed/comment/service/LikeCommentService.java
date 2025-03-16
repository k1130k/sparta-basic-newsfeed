package com.example.basicnewfeed.comment.service;

import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.comment.entity.Comment;
import com.example.basicnewfeed.comment.entity.LikeComment;
import com.example.basicnewfeed.comment.repository.CommentRepository;
import com.example.basicnewfeed.comment.repository.LikeCommentRepository;
import com.example.basicnewfeed.post.entity.LikePost;
import com.example.basicnewfeed.post.entity.Post;
import com.example.basicnewfeed.post.repository.PostRepository;
import com.example.basicnewfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeCommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    // 댓글 좋아요
    @Transactional
    public void likeComment(AuthUser authUser, Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시글입니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 댓글입니다.")
        );

        // 본인 게시물 여부 확인

        if(authUser.getId().equals(comment.getUser().getId())) {
            throw new IllegalStateException("본인 댓글에는 좋아요를 할 수 없습니다.");
        }

        // 좋아요 중복 체크
        boolean exists = likeCommentRepository.existsByCommentAndUserId(comment, authUser.getId());
        if(exists) {
            throw new IllegalStateException("댓글 좋아요는 1번만 가능합니다.");
        }

        //좋아요 추가
        User user = new User(authUser.getId(), authUser.getEmail());
        LikeComment likeComment = new LikeComment(comment, user, post);
        likeCommentRepository.save(likeComment);

        // 좋아요 카운트 증가
        comment.increaseLikeComment();
    }

    // 댓글 좋아요 취소
    @Transactional
    public void delete(AuthUser authUser, Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글이 존재하지않습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("댓글이 존재하지 않습니다.")
        );

        // 좋아요 취소 중복 체크
        boolean exists = likeCommentRepository.existsByCommentAndUserId(comment, authUser.getId());
        if(!exists) {
            throw new IllegalStateException("좋아요가 안되어있는 상태입니다.");
        }

//        // 좋아요 본인확인
//        LikeComment likeComment = likeCommentRepository.findByCommentAndUserId(comment,authUser.getId())
//                .orElseThrow(() -> new IllegalStateException("좋아요 기록이 존재하지 않습니다."));
//        if(!authUser.getId().equals(likeComment.getUser().getId())) {
//            throw new IllegalStateException("본인만 좋아요 취소가 가능합니다.");
//        }

        // 좋아요 취소

        likeCommentRepository.deleteByCommentAndUserId(comment, authUser.getId());

        // 댓글 좋아요 카운트 감소

        comment.decreaseLikeComment();
    }
}
