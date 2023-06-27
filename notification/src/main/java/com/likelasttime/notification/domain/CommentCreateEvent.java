package com.likelasttime.notification.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCreateEvent {

    private final Comment comment;
}
