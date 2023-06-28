package com.likelasttime.notification.domain;


import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime pushTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PushStatus pushStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PushCase pushCase;

    private Long pathId;

    @Builder
    public Notification(
            String message,
            LocalDateTime pushTime,
            PushStatus pushStatus,
            User user,
            PushCase pushCase,
            Long pathId) {
        this.message = message;
        this.pushTime = pushTime;
        this.pushStatus = pushStatus;
        this.user = user;
        this.pushCase = pushCase;
        this.pathId = pathId;
    }

    public boolean isPushable(LocalDateTime now) {
        return pushStatus == PushStatus.IN_COMPLETE
                && (pushTime.isBefore(now) || pushTime.isEqual(now));
    }
}
