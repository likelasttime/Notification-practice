package com.likelasttime.notification.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelasttime.notification.dto.request.PostRequest;
import com.likelasttime.notification.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @MockBean PostService postService;

    @Test
    @DisplayName("게시글 생성")
    public void createPostTest() throws Exception {
        long userId = 1L;
        PostRequest postRequest = new PostRequest(userId, "hi");

        when(postService.createPost(any())).thenReturn(userId);

        mockMvc.perform(
                        post("/posts")
                                .content(objectMapper.writeValueAsString(postRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isOk(), content().string("1"));
    }
}
