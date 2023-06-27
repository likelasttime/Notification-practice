package com.likelasttime.notification.event;


import com.likelasttime.notification.domain.*;
import com.likelasttime.notification.service.CommentService;
import com.likelasttime.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CommentPushEventListener {

    private final NotificationService notificationService;
    private final CommentService commentService;

    @Transactional
    @Async("asyncExecutor")
    @TransactionalEventListener
    public void handle(CommentCreateEvent event) {
        Comment comment = commentService.find(event.getComment().getId());
        User owner = extractWriter(comment);
        if (isSelfCommented(comment, owner)) {
            return;
        }
        notificationService.create(buildNotification(comment));
    }

    private User extractWriter(Comment comment) {
        return comment.getUser();
    }

    private boolean isSelfCommented(Comment comment, User writer) {
        return writer.matchId(comment.getUser().getId());
    }

    public Notification buildNotification(Comment comment) {
        User commentWriter = comment.getUser();
        User owner = extractWriter(comment);
        return Notification.builder()
                .message(commentWriter.getEmail() + "님께서 회원님의 포스트에 댓글을 남겼어요!")
                .pushTime(comment.getCreatedAt())
                .pushStatus(PushStatus.IN_COMPLETE)
                .pushCase(PushCase.COMMENT)
                .user(owner)
                .pathId(comment.getPost().getId())
                .build();
    }
}
