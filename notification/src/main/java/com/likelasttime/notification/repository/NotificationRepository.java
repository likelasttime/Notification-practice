package com.likelasttime.notification.repository;


import com.likelasttime.notification.domain.Notification;
import com.likelasttime.notification.domain.PushCase;
import com.likelasttime.notification.domain.PushStatus;
import java.util.List;
import java.util.Optional;

import com.likelasttime.notification.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByPathIdAndPushStatusAndPushCase(
            Long pathId, PushStatus pushStatus, PushCase pushCase);

    List<Notification> findByPushStatus(PushStatus pushStatus);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Notification pn set pn.pushStatus = :pushStatus where pn in :notifications")
    void updatePushStatusIn(
            @Param("notifications") List<Notification> notifications,
            @Param("pushStatus") PushStatus pushStatus);

    @Query("select pn from Notification pn "
            + "where pn.user = :user and pn.pushStatus = :pushStatus "
            + "order by pn.pushTime desc")
    List<Notification> findAllLatestOrderByDesc(@Param("user") User user,
                                                @Param("pushStatus") PushStatus pushStatus);
}
