package com.example.basicnewfeed.user.entity;

import com.example.basicnewfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String nickName;

    @Column(nullable = false)
    private int followingCount = 0;

    @Column(nullable = false)
    private int followerCount = 0;

    public User(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public void update(String nickName) {
        this.nickName = nickName;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void increaseFollowingCount() {
        this.followingCount++;
    }

    public void increaseFollowerCount() {
        this.followerCount++;
    }

    public void decreaseFollowingCount() {
        if(followingCount > 0) {
            this.followingCount--;
        }
    }

    public void decreaseFollowerCount() {
        if (followerCount > 0) {
            this.followerCount--;
        }
    }
}
