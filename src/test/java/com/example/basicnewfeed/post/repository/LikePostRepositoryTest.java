package com.example.basicnewfeed.post.repository;

import com.example.basicnewfeed.post.entity.LikePost;
import com.example.basicnewfeed.post.entity.Post;
import com.example.basicnewfeed.user.entity.User;
import com.example.basicnewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LikePostRepositoryTest {

    @Autowired
    LikePostRepository likePostRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void 좋아요를_할_수있다() {
        // given
        String email = "test@naver.com";
        String password ="qweQ12!";
        String nickName = "김정은";
        User user = new User(email, password, nickName);
        userRepository.save(user);

        String content = "안녕?";
        Post post = new Post(user, content);
        postRepository.save(post);

        LikePost likePost = new LikePost(post, user);
        likePostRepository.save(likePost);

        // when
        List<LikePost> likePosts = likePostRepository.findAll();

        // then
        assertNotNull(likePosts);
        assertThat(likePosts.size()).isEqualTo(1);
    }

}