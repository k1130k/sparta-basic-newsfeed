package com.example.basicnewfeed.comment.entity;

import com.example.basicnewfeed.common.entity.BaseEntity;
import com.example.basicnewfeed.post.entity.Post;
import com.example.basicnewfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private int likeComment = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(String comment, Post post, User user) {
        this.comment = comment;
        this.post = post;
        this.user = user;
    }

    public void update(String comment) {
        this.comment = comment;
    }

    public void increaseLikeComment() {
        this.likeComment++;
    }

    public void decreaseLikeComment() {
        this.likeComment--;
    }
}
