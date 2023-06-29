package com.likelasttime.notification.dto.request.response;


import com.likelasttime.notification.domain.Notification;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long notificationId;
    private String message;
    private LocalDateTime pushTime;
    private Long pathId;
    private String pushCase;

    public NotificationResponse(Notification notification) {
        this.notificationId = notification.getId();
        this.message = notification.getMessage();
        this.pushTime = notification.getPushTime();
        this.pathId = notification.getPathId();
        this.pushCase = notification.getPushCase().name().toLowerCase();
    }
}
