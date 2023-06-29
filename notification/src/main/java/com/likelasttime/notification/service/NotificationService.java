package com.likelasttime.notification.service;


import com.likelasttime.notification.domain.Notification;
import com.likelasttime.notification.domain.PushCase;
import com.likelasttime.notification.domain.PushStatus;
import com.likelasttime.notification.domain.User;
import com.likelasttime.notification.dto.request.response.NotificationResponse;
import com.likelasttime.notification.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Transactional
    public void create(Notification pushNotification) {
        notificationRepository.save(pushNotification);
    }

    public Optional<Notification> searchByPathAndStatusAndPushCase(
            Long pathId, PushStatus status, PushCase pushCase) {
        return notificationRepository.findByPathIdAndPushStatusAndPushCase(
                pathId, status, pushCase);
    }

    public List<Notification> searchPushable() {
        final LocalDateTime now = LocalDateTime.now();
        return notificationRepository.findByPushStatus(PushStatus.IN_COMPLETE).stream()
                .filter(notification -> notification.isPushable(now))
                .collect(Collectors.toList());
    }

    @Transactional
    public void completeAll(List<Notification> notifications) {
        notificationRepository.updatePushStatusIn(notifications, PushStatus.COMPLETE);
    }

    public List<Notification> findAllLatestOrderByDesc(User user, PushStatus pushStatus) {
        return notificationRepository.findAllLatestOrderByDesc(user, pushStatus);
    }

    @Transactional
    public void deleteCompletedByUser(User user) {
        notificationRepository.deleteByUserAndPushStatus(user, PushStatus.COMPLETE);
    }

    public List<NotificationResponse> findCompleteNotificationsByMe(Long userId) {
        User user = userService.search(userId);
        List<Notification> pushNotifications = findAllLatestOrderByDesc(user, PushStatus.COMPLETE);
        return pushNotifications.stream()
                .map(NotificationResponse::new)
                .collect(Collectors.toList());
    }
}
