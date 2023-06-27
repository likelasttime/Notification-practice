package com.likelasttime.notification.repository;


import com.likelasttime.notification.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
