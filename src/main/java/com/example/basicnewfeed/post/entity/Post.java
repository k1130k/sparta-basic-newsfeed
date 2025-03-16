package com.example.basicnewfeed.post.entity;

import com.example.basicnewfeed.common.entity.BaseEntity;
import com.example.basicnewfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;
    private int likePost = 0;

    public Post(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }

    // 좋아요수 증가 메서드
    public void increaseLikePost() {
        this.likePost++;
    }

    public void decreaseLikePost() {
        this.likePost--;
    }
}
