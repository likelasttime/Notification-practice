package com.likelasttime.notification.service;


import com.likelasttime.notification.domain.Comment;
import com.likelasttime.notification.domain.CommentCreateEvent;
import com.likelasttime.notification.domain.Post;
import com.likelasttime.notification.dto.request.CommentRequest;
import com.likelasttime.notification.repository.CommentRepository;
import com.likelasttime.notification.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Long createComment(CommentRequest commentRequest) {
        Post savedPost =
                postRepository
                        .findById(commentRequest.getPostId())
                        .orElseThrow(RuntimeException::new);
        Comment comment =
                commentRepository.save(
                        new Comment(savedPost.getUser(), savedPost, commentRequest.getContent()));

        applicationEventPublisher.publishEvent(new CommentCreateEvent(comment));

        return comment.getId();
    }
}
