package com.likelasttime.notification.repository;


import com.likelasttime.notification.domain.Notification;
import com.likelasttime.notification.domain.PushCase;
import com.likelasttime.notification.domain.PushStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByPathIdAndPushStatusAndPushCase(
            Long pathId, PushStatus pushStatus, PushCase pushCase);

    List<Notification> findByPushStatus(PushStatus pushStatus);
}
