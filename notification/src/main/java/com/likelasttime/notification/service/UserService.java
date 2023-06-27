package com.likelasttime.notification.service;


import com.likelasttime.notification.domain.User;
import com.likelasttime.notification.dto.request.UserRequest;
import com.likelasttime.notification.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void joinUser(UserRequest userRequest) {
        userRepository.save(new User(userRequest.getEmail()));
    }
}
