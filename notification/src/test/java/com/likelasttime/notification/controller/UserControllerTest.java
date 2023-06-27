package com.likelasttime.notification.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelasttime.notification.dto.request.UserRequest;
import com.likelasttime.notification.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @MockBean UserService userService;

    @Test
    @DisplayName("회원 가입")
    public void joinUserTest() throws Exception {
        UserRequest userRequest = new UserRequest("test@test.com");

        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(userRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isOk());
    }
}
