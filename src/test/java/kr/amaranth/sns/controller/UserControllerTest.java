package kr.amaranth.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.amaranth.sns.controller.request.UserJoinRequest;
import kr.amaranth.sns.controller.request.UserLoginRequest;
import kr.amaranth.sns.exception.ErrorCode;
import kr.amaranth.sns.exception.SnsApplicationException;
import kr.amaranth.sns.model.User;
import kr.amaranth.sns.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @DisplayName("회원가입 성공 테스트")
    @Test
    public void 회원가입() throws Exception {
        // Given
        String userName = "userName";
        String password = "password";

        // TODO : mocking
        when(userService.join(userName, password)).thenReturn(mock(User.class));

        // When & Then
        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserJoinRequest(userName, password)))
            ).andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("회원가입 실패 테스트")
    @Test
    public void 회원가입시_이미_등록된_username으로_회원가입을_하는_경우_에러반환() throws Exception {
        // Given
        String userName = "userName";
        String password = "password";

        when(userService.join(userName, password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        // When & Then
        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserJoinRequest(userName, password)))
            ).andDo(print())
            .andExpect(status().isConflict());
    }

    @DisplayName("로그인 성공 테스트")
    @Test
    public void 로그인() throws Exception {
        // Given
        String userName = "userName";
        String password = "password";

        // TODO : mocking
        when(userService.login(userName, password)).thenReturn("test_token");

        // When & Then
        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserLoginRequest(userName, password)))
            ).andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("로그인 실패 테스트")
    @Test
    public void 로그인시_회원가입이_안된_username을_입력할경우_에러반환() throws Exception {
        // Given
        String userName = "userName";
        String password = "password";

        when(userService.login(userName, password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        // When & Then
        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserLoginRequest(userName, password)))
            ).andDo(print())
            .andExpect(status().isNotFound());
    }

    @DisplayName("로그인 실패 테스트")
    @Test
    public void 로그인시_틀린_password를_입력할경우_에러반환() throws Exception {
        // Given
        String userName = "userName";
        String password = "password";

        when(userService.login(userName, password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        // When & Then
        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserLoginRequest(userName, password)))
            ).andDo(print())
            .andExpect(status().isUnauthorized());
    }

}
