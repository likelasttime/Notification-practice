package com.likelasttime.notification.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.likelasttime.notification.dto.request.UserRequest;
import com.likelasttime.notification.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired UserService userService;

    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("회원가입")
    void joinUser() {
        // given
        UserRequest userRequest = new UserRequest("test@test.com");

        // when
        userService.joinUser(userRequest);

        // then
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }
}
