package com.likelasttime.notification.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelasttime.notification.dto.request.response.NotificationResponse;
import com.likelasttime.notification.service.NotificationService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = NotificationController.class)
public class NotificationControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @MockBean NotificationService notificationService;

    @Test
    @DisplayName("안 읽은 알림을 조회하면 200을 응답한다.")
    void findNotifications() throws Exception {
        // given
        NotificationResponse notificationResponse1 =
                new NotificationResponse(
                        1L, "**님께서 회원님의 포스트에 댓글을 남겼어요!", LocalDateTime.now(), 1L, "comment");
        NotificationResponse notificationResponse2 =
                new NotificationResponse(
                        2L, "**님께서 회원님의 포스트에 댓글을 남겼어요!", LocalDateTime.now(), 2L, "comment");
        List<NotificationResponse> responses =
                List.of(notificationResponse1, notificationResponse2);
        given(notificationService.findCompleteNotificationsByMe(any())).willReturn(responses);

        // when
        ResultActions result = mockMvc.perform(get("/push-notifications").param("userId", "1"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responses)))
                .andDo(print());
    }
}
