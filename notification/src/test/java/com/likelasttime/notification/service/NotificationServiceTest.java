package com.likelasttime.notification.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.likelasttime.notification.domain.Notification;
import com.likelasttime.notification.domain.PushCase;
import com.likelasttime.notification.domain.PushStatus;
import com.likelasttime.notification.domain.User;
import com.likelasttime.notification.repository.NotificationRepository;
import com.likelasttime.notification.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
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
        notificationRepository.deleteAll();
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

    @DisplayName("발송 가능한 알림 조회")
    @Test
    void searchPushable() {
        // given
        User user = userRepository.findAll().get(0);
        LocalDateTime now = LocalDateTime.now();

        Notification notification =
                Notification.builder()
                        .user(user)
                        .pathId(1L)
                        .pushTime(now.minusMinutes(2L))
                        .pushStatus(PushStatus.IN_COMPLETE)
                        .pushCase(PushCase.COMMENT)
                        .message("알림 전송")
                        .build();

        notificationRepository.save(notification);

        Notification notification1 =
                Notification.builder()
                        .user(user)
                        .pathId(2L)
                        .pushTime(now.minusMinutes(1L))
                        .pushStatus(PushStatus.IN_COMPLETE)
                        .pushCase(PushCase.COMMENT)
                        .message("알림 전송")
                        .build();

        notificationRepository.save(notification1);

        Notification notification2 =
                Notification.builder()
                        .user(user)
                        .pathId(3L)
                        .pushTime(now.minusHours(1L))
                        .pushStatus(PushStatus.COMPLETE)
                        .pushCase(PushCase.COMMENT)
                        .message("알림 전송")
                        .build();

        notificationRepository.save(notification2);

        // when
        List<Notification> actual = notificationService.searchPushable();

        // then
        assertThat(actual).map(Notification::getPathId).containsOnly(1L, 2L);
    }
}
