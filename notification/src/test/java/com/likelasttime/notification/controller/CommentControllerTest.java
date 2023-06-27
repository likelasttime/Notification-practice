package com.likelasttime.notification.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelasttime.notification.dto.request.CommentRequest;
import com.likelasttime.notification.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @MockBean CommentService commentService;

    @Test
    @DisplayName("댓글 생성")
    public void createCommentTest() throws Exception {
        Long commentId = 1L;
        CommentRequest commentRequest = new CommentRequest(1L, "hi");

        when(commentService.createComment(any())).thenReturn(commentId);

        mockMvc.perform(
                        post("/comments")
                                .content(objectMapper.writeValueAsString(commentRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isOk(), content().string(commentId.toString()));
    }
}
