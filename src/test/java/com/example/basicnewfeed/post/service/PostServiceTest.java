package com.example.basicnewfeed.post.service;

import com.example.basicnewfeed.auth.dto.AuthUser;
import com.example.basicnewfeed.follow.repository.FollowRepository;
import com.example.basicnewfeed.post.dto.PostSaveRequestDto;
import com.example.basicnewfeed.post.dto.PostSaveResponseDto;
import com.example.basicnewfeed.post.entity.Post;
import com.example.basicnewfeed.post.repository.PostRepository;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;

    @Test
    void 게시글_작성을_한다() {
        // given
        Long id = 1L;
        String email = "test@naver.com";
        String password = "!Qw1234567";
        String content = "김정은TV 구독과 좋아요 알람설정까지 부탁해요~";
        String nickName = "김정은";
        LocalDateTime now = LocalDateTime.now();

        // AuthUser 객체 생성
        AuthUser authUser = new AuthUser(id, email);

        // DTO 생성
        PostSaveRequestDto requestDto = new PostSaveRequestDto(content);
        ReflectionTestUtils.setField(requestDto, "content", content);

        // User 객체 생성
        User user = new User(email, password, nickName);
        ReflectionTestUtils.setField(user, "id", id);

        // Post 객체 생성
        Post post = new Post(user, content);
        ReflectionTestUtils.setField(post, "id", 1L);
        ReflectionTestUtils.setField(post, "createdAt", now);
        ReflectionTestUtils.setField(post, "updatedAt", now);
        ReflectionTestUtils.setField(post, "likePost", 0);

        // Mock 동작 설정
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when
        PostSaveResponseDto response = postService.save(authUser, requestDto);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNickName()).isEqualTo(nickName);
        assertThat(response.getContent()).isEqualTo(content);
        assertThat(response.getLikePost()).isEqualTo(0);
        assertThat(response.getCreatedAt()).isEqualTo(now);
        assertThat(response.getUpdatedAt()).isEqualTo(now);

        // 메소드 호출 검증
        verify(userRepository).findById(id);
        verify(postRepository).save(any(Post.class));
    }


}