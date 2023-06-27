package com.likelasttime.notification.controller;


import com.likelasttime.notification.dto.request.UserRequest;
import com.likelasttime.notification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<String> joinUser(@RequestBody UserRequest userRequest) {
        userService.joinUser(userRequest);
        return ResponseEntity.ok("요청에 성공하였습니다.");
    }
}
