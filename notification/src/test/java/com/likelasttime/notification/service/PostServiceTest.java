package com.likelasttime.notification.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.likelasttime.notification.domain.User;
import com.likelasttime.notification.dto.request.PostRequest;
import com.likelasttime.notification.repository.PostRepository;
import com.likelasttime.notification.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTest {

    @Autowired PostService postService;

    @Autowired PostRepository postRepository;

    @Autowired UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(new User("test@test.com"));
    }

    @AfterEach
    void afterSetUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 생성")
    void createPost() {
        // given
        User savedUser = userRepository.findAll().get(0);
        PostRequest postRequest = new PostRequest(savedUser.getId(), "hi");

        // when
        postService.createPost(postRequest);

        // then
        assertThat(postRepository.findAll().size()).isEqualTo(1);
    }
}
