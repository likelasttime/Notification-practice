package com.likelasttime.notification.service;


import com.likelasttime.notification.domain.Notification;
import com.likelasttime.notification.domain.PushCase;
import com.likelasttime.notification.domain.PushStatus;
import com.likelasttime.notification.repository.NotificationRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void create(Notification pushNotification) {
        notificationRepository.save(pushNotification);
    }

    public Optional<Notification> searchByPathAndStatusAndPushCase(
            Long pathId, PushStatus status, PushCase pushCase) {
        return notificationRepository.findByPathIdAndPushStatusAndPushCase(
                pathId, status, pushCase);
    }
}
