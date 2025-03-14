package com.example.basicnewfeed.post.repository;

import com.example.basicnewfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOrderByUpdatedAtDesc(Pageable pageable);

}
