package com.likelasttime.notification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @Test
    @DisplayName("발송 가능한 알림 조회")
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

    @Test
    @DisplayName("알림들을 모두 발송 완료 상태로 변경")
    void completeAll() {
        // given
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.findAll().get(0);

        Notification notification1 =
                Notification.builder()
                        .user(user)
                        .pathId(1L)
                        .pushTime(now.minusMinutes(2L))
                        .pushStatus(PushStatus.IN_COMPLETE)
                        .pushCase(PushCase.COMMENT)
                        .message("알림 전송")
                        .build();

        notificationRepository.save(notification1);

        Notification notification2 =
                Notification.builder()
                        .user(user)
                        .pathId(2L)
                        .pushTime(now.minusMinutes(3L))
                        .pushStatus(PushStatus.IN_COMPLETE)
                        .pushCase(PushCase.COMMENT)
                        .message("알림 전송")
                        .build();

        notificationRepository.save(notification2);

        List<Notification> notifications = List.of(notification1, notification2);

        // when
        notificationService.completeAll(notifications);

        // then
        assertThat(notificationRepository.findAll())
                .map(Notification::getPushStatus)
                .containsExactly(PushStatus.COMPLETE, PushStatus.COMPLETE);
    }

    @Test
    @DisplayName("발송된 알림 조회")
    void findAllLatestOrderByDesc() {
        // given
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.findAll().get(0);

        Notification notification1 =
                Notification.builder()
                        .user(user)
                        .pathId(1L)
                        .pushTime(now)
                        .pushStatus(PushStatus.COMPLETE)
                        .pushCase(PushCase.COMMENT)
                        .message("알림 전송")
                        .build();

        notificationRepository.save(notification1);

        Notification notification2 =
                Notification.builder()
                        .user(user)
                        .pathId(2L)
                        .pushTime(now.minusDays(1))
                        .pushStatus(PushStatus.COMPLETE)
                        .pushCase(PushCase.COMMENT)
                        .message("알림 전송")
                        .build();

        notificationRepository.save(notification2);

        Notification notification3 =
                Notification.builder()
                        .user(user)
                        .pathId(3L)
                        .pushTime(now.minusDays(2))
                        .pushStatus(PushStatus.IN_COMPLETE)
                        .pushCase(PushCase.COMMENT)
                        .message("알림 전송")
                        .build();

        notificationRepository.save(notification3);

        // when
        List<Notification> actual =
                notificationService.findAllLatestOrderByDesc(user, PushStatus.COMPLETE);

        // then
        assertAll(
                () -> assertThat(actual).hasSize(2),
                () -> assertThat(actual).map(Notification::getPathId).containsExactly(1L, 2L));
    }
}
