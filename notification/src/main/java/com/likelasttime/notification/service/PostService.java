package com.likelasttime.notification.service;


import com.likelasttime.notification.domain.Post;
import com.likelasttime.notification.domain.User;
import com.likelasttime.notification.dto.request.PostRequest;
import com.likelasttime.notification.repository.PostRepository;
import com.likelasttime.notification.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createPost(PostRequest postRequest) {
        User createdBy =
                userRepository.findById(postRequest.getUserId()).orElseThrow(RuntimeException::new);
        return postRepository.save(new Post(createdBy, postRequest.getContent())).getId();
    }
}
