package com.example.basicnewfeed.post.service;

import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.post.entity.LikePost;
import com.example.basicnewfeed.post.entity.Post;
import com.example.basicnewfeed.post.repository.LikePostRepository;
import com.example.basicnewfeed.post.repository.PostRepository;
import com.example.basicnewfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikePostService {

    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;

    // 게시글 좋아요
    @Transactional
    public void likePost(AuthUser authUser, Long postId) {

        // 게시글 존재 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글이 존재하지 않습니다."));

        // 본인 게시물 여부 확인

        if(authUser.getId().equals(post.getUser().getId())) {
            throw new IllegalStateException("본인 게시물에는 좋아요를 할 수 없습니다.");
        }

        // 좋아요 중복 체크
        boolean exists = likePostRepository.existsByPostAndUserId(post, authUser.getId());
        if(exists) {
            throw new IllegalStateException("게시글 좋아요는 1번만 가능합니다.");
        }

        //좋아요 추가
        User user = new User(authUser.getId(), authUser.getEmail());
        LikePost likePost = new LikePost(post, user);
        likePostRepository.save(likePost);

        // 게시글 좋아요 카운트 증가
        post.increaseLikePost();
    }

    // 좋아요 취소
    @Transactional
    public void deleteById(AuthUser authUser, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글이 존재하지 않습니다.")
        );

        // 좋아요 취소 중복 체크
        boolean exists = likePostRepository.existsByPostAndUserId(post, authUser.getId());
        if(!exists) {
            throw new IllegalStateException("좋아요가 안되어있는 상태입니다.");
        }

        // 좋아요 취소

        likePostRepository.deleteByPostAndUserId(post, authUser.getId());

        // 게시글 좋아요 카운트 감소
        post.decreaseLikePost();
    }
}
