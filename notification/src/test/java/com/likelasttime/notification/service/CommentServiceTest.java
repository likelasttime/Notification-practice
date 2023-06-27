package com.likelasttime.notification.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.likelasttime.notification.domain.Post;
import com.likelasttime.notification.domain.User;
import com.likelasttime.notification.dto.request.CommentRequest;
import com.likelasttime.notification.repository.CommentRepository;
import com.likelasttime.notification.repository.PostRepository;
import com.likelasttime.notification.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentServiceTest {

    @Autowired CommentService commentService;

    @Autowired PostRepository postRepository;

    @Autowired UserRepository userRepository;

    @Autowired CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        User savedUser = userRepository.save(new User("test@test.com"));
        postRepository.save(new Post(savedUser, "hi"));
    }

    @AfterEach
    void afterSetUp() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 생성")
    void createComment() {
        // given
        Post savedPost = postRepository.findAll().get(0);
        CommentRequest commentRequest = new CommentRequest(savedPost.getId(), "hi");

        // when
        commentService.createComment(commentRequest);

        // then
        assertThat(commentRepository.findAll().size()).isEqualTo(1);
    }
}
