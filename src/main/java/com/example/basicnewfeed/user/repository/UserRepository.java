package com.example.basicnewfeed.user.repository;

import com.example.basicnewfeed.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);

    boolean existsByPassword(String oldPassword);
}
