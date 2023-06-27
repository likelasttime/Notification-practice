package com.likelasttime.notification.repository;


import com.likelasttime.notification.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
