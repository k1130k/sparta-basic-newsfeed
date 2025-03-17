package com.example.basicnewfeed.post.repository;

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
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void 게시글_전체를_가져올_수_있다() {
        // given
        String email = "test@naver.com";
        String password ="qweQ12!";
        String nickName = "김정은";
        User user = new User(email, password, nickName);
        userRepository.save(user);

        String content = "안녕?";
        Post post = new Post(user, content);
        postRepository.save(post);

        // when
        List<Post> posts = postRepository.findAll();

        // then
        assertNotNull(posts);
        assertThat(posts.size()).isEqualTo(1);
    }
  
}