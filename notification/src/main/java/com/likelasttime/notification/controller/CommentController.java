package com.likelasttime.notification.controller;


import com.likelasttime.notification.dto.request.CommentRequest;
import com.likelasttime.notification.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<Long> createComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.createComment(commentRequest));
    }
}
