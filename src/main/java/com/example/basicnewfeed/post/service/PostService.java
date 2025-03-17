package com.example.basicnewfeed.post.service;

import com.example.basicnewfeed.auth.annotation.Auth;
import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.follow.entity.Follow;
import com.example.basicnewfeed.follow.repository.FollowRepository;
import com.example.basicnewfeed.post.dto.*;
import com.example.basicnewfeed.post.entity.LikePost;
import com.example.basicnewfeed.post.entity.Post;
import com.example.basicnewfeed.post.repository.LikePostRepository;
import com.example.basicnewfeed.post.repository.PostRepository;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 게시글 작성
    @Transactional
    public PostSaveResponseDto save(AuthUser authUser, PostSaveRequestDto dto) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
        Post post = new Post(user, dto.getContent());
        Post savedPost = postRepository.save(post);
        return new PostSaveResponseDto(savedPost.getId(),
                savedPost.getUser().getNickName(),
                savedPost.getContent(),
                savedPost.getLikePost(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt());
    }

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAll(AuthUser authUser, Pageable pageable) {
            Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        return posts.map(post -> new PostResponseDto(
                post.getId(),
                post.getUser().getNickName(),
                post.getContent(),
                post.getLikePost(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public PostResponseDto findOne(AuthUser authUser, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글이 존재하지 않습니다."));
        return new PostResponseDto(
                post.getId(),
                post.getUser().getNickName(),
                post.getContent(),
                post.getLikePost(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    // 게시물 조건조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> search(
            AuthUser authUser,
            LocalDate startDate,
            LocalDate endDate,
            String sortBy,
            Pageable pageable) {

        // 정렬 조건
        Sort sort;
        if ("likes".equals(sortBy)) {
            sort = Sort.by(Sort.Direction.DESC, "likePost");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "updatedAt");
        }

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Post> posts;

        if (startDate != null && endDate != null) {
            posts = postRepository.findByCreatedAtBetween(
                    startDate.atStartOfDay(),
                    endDate.atTime(23, 59, 59),
                    pageable
            );
        } else {
            posts = postRepository.findAll(pageable);
        }

        return posts.map(post -> new PostResponseDto(
                post.getId(),
                post.getUser().getNickName(),
                post.getContent(),
                post.getLikePost(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    // 팔로잉하고있는 사람 게시글 보기
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getFollowingPosts(AuthUser authUser, Pageable pageable) {
        User following = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        List<Follow> followList = followRepository.findAllByFollower(following); // 2. follow db에서 팔로우한 사람들을 리스트로 추출
        List<User> users = new ArrayList<>(); // 3. user 리스트 생성해서 follow에다가 집어넣음.
        for (Follow follow : followList) {
            users.add(follow.getFollowing());
        }

        Page<Post> posts = postRepository.findByUserInOrderByCreatedAtDesc(users, pageable);
        return posts.map(post -> new PostResponseDto(
                post.getId(),
                post.getUser().getNickName(),
                post.getContent(),
                post.getLikePost(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    // 게시글 수정
    @Transactional
    public PostUpdateResponseDto update(AuthUser authUser, PostUpdateRequestDto dto, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시물입니다.")
        );
        if (!authUser.getId().equals(post.getUser().getId())) {
            throw new IllegalStateException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }
        post.update(dto.getContent());
        return new PostUpdateResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getContent(),
                post.getLikePost(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    //게시글 삭제
    @Transactional
    public void deleteById(AuthUser authUser, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글이 존재하지 않습니다.")
        );
        if (!authUser.getId().equals(post.getUser().getId())) {
            throw new IllegalStateException("작성자 본인만 게시글 삭제가 가능합니다.");
        }

        postRepository.deleteById(postId);
    }
}


