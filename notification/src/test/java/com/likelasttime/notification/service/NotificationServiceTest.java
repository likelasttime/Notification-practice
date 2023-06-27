package com.likelasttime.notification.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.likelasttime.notification.domain.Notification;
import com.likelasttime.notification.domain.PushCase;
import com.likelasttime.notification.domain.PushStatus;
import com.likelasttime.notification.domain.User;
import com.likelasttime.notification.repository.NotificationRepository;
import com.likelasttime.notification.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired private NotificationService notificationService;

    @Autowired private NotificationRepository notificationRepository;

    @Autowired private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(new User("test@test.com"));
    }

    @AfterEach
    void afterSetUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("알림 생성")
    void create() {
        // given
        User user = userRepository.findAll().get(0);
        Notification notification =
                new Notification(
                        "알림",
                        LocalDateTime.now(),
                        PushStatus.IN_COMPLETE,
                        user,
                        PushCase.COMMENT,
                        1L);

        // when
        notificationService.create(notification);

        // then
        assertThat(notificationRepository.findById(notification.getId())).isPresent();
    }
}
