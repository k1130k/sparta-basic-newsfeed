package com.example.basicnewfeed.post.service;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.post.dto.*;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostSaveResponseDto save(AuthUser authUser, PostSaveRequestDto dto) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
        Post post = new Post(user, dto.getContent());
        Post savedPost = postRepository.save(post);
        return new PostSaveResponseDto(savedPost.getId(),  savedPost.getContent(), savedPost.getUser().getId());

    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> dtos = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto dto = new PostResponseDto(
                    post.getId(),
                    post.getUser().getId(),
                    post.getContent(),
                    post.getLikePost());
            dtos.add(dto);
        }
        return dtos;
    }

    public PostResponseDto findOne(AuthUser authUser, @Valid Long postId) {
        Post post = postRepository.findById(postId) .orElseThrow(
                () -> new IllegalStateException("게시글이 존재하지 않습니다."));
        return new PostResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getContent(),
                post.getLikePost());
    }

    @Transactional
    public PostUpdateResponseDto update(AuthUser authUser, @Valid PostUpdateRequestDto dto, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시물입니다.")
        );
        if(!authUser.getId().equals(post.getUser().getId())) {
            throw new IllegalStateException("본인이 작성한 스케줄만 수정할 수 있습니다.");
        }
        post.update(dto.getContent());
        return new PostUpdateResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getContent(),
                post.getLikePost()
        );
    }

    @Transactional
    public void deleteById(AuthUser authUser, @Valid Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글이 존재하지 않습니다.")
        );
        if(!authUser.getId().equals(post.getUser().getId())) {
            throw new IllegalStateException("작성자 본인만 게시글 삭제가 가능합니다.");
        }

        postRepository.deleteById(postId);
    }
}


