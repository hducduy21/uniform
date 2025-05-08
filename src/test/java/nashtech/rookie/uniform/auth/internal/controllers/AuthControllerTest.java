package nashtech.rookie.uniform.auth.internal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nashtech.rookie.uniform.auth.internal.dtos.request.AuthRequest;
import nashtech.rookie.uniform.auth.internal.dtos.response.AuthResponse;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;
import nashtech.rookie.uniform.auth.internal.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void login_ValidRequest_ReturnsAuthResponse() throws Exception {
        AuthRequest authRequest = new AuthRequest("user@gmail.com", "123456789");
        AuthResponse authResponse = new AuthResponse();
        when(authService.authenticate(any(AuthRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        verify(authService).authenticate(any(AuthRequest.class));
    }

    @Test
    void login_InvalidPassword_ReturnOk() throws Exception {
        AuthRequest authRequest = new AuthRequest("user@gmail.com", "invalidpassword");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk());

        verify(authService).authenticate(any(AuthRequest.class));
    }

    @Test
    void login_InvalidEmail_ReturnBadRequest() throws Exception {
        AuthRequest authRequest = new AuthRequest("", "123456");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).authenticate(any(AuthRequest.class));
    }

    @Test
    void login_InvalidEmail_ReturnsAuthResponse() throws Exception {
        AuthRequest authRequest = new AuthRequest(null, "");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).authenticate(any(AuthRequest.class));
    }

    @Test
    void refresh_ValidToken_ReturnsTokenPair() throws Exception {
        String refreshToken = "valid-refresh-token";
        TokenPair tokenPair = TokenPair.builder().accessToken("access-token").refreshToken("refresh-token").build();
        when(authService.getAccessToken(refreshToken)).thenReturn(tokenPair);

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .header("Authorization", refreshToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));

        verify(authService, times(1)).getAccessToken(refreshToken);
    }

    @Test
    void refresh_MissingAuthorizationHeader_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest());

        verify(authService, never()).getAccessToken(anyString());
    }

}