package com.likelasttime.notification.controller;


import com.likelasttime.notification.dto.request.response.NotificationResponse;
import com.likelasttime.notification.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/push-notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> findCompleteNotificationsByMe(
            @RequestParam final Long userId) {
        return ResponseEntity.ok(notificationService.findCompleteNotificationsByMe(userId));
    }
}
