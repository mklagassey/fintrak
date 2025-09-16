package com.fintrak.userservice.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintrak.userservice.controller.UserController;
import com.fintrak.userservice.dto.LoginRequest;
import com.fintrak.userservice.dto.RegistrationRequest;
import com.fintrak.userservice.model.User;
import com.fintrak.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    private RegistrationRequest registrationRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testuser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
        user.setEmail("test@example.com");
    }

    @Test
    void whenRegisterUser_thenReturnsCreated() throws Exception {
        // Arrange
        doNothing().when(userService).registerUser(any(RegistrationRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));

        verify(userService, times(1)).registerUser(any(RegistrationRequest.class));
    }

    @Test
    void whenLoginUser_thenReturnsToken() throws Exception {
        // Arrange
        String jwtToken = "dummy.jwt.token";
        when(userService.login(any(LoginRequest.class))).thenReturn(jwtToken);

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(jwtToken));

        verify(userService, times(1)).login(any(LoginRequest.class));
    }

    @Test
    void whenGetUserProfileWithAuthenticatedUser_thenReturnsUserProfile() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/users/profile")
                        .with(user(this.user))) // Mock the @AuthenticationPrincipal
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    @WithMockUser // For endpoints that only require authentication, not a specific principal object
    void whenGetUserProfileWithoutPrincipalObject_thenShouldStillWorkIfSecurityIsLenient() throws Exception {
        // This test is more about demonstrating how to test a secured endpoint
        // where you don't need to mock the specific User object.
        // In your case, getUserProfile returns the User object, so the previous test is more accurate.
        // However, if the principal was just a String (e.g., username), this would be sufficient.

        // Since the endpoint expects a User object, and we are not providing one,
        // the 'user' parameter will be null. The test will check for an OK status
        // and an empty body, which is what Spring MVC will return for a null object.
        mockMvc.perform(get("/api/v1/users/profile"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void whenGetUserProfileUnauthenticated_thenReturnsOkWithEmptyBody() throws Exception {
        // Act & Assert
        // Since this is a standalone setup, security filters are not applied.
        // An unauthenticated request will result in a null principal.
        // The controller will return 200 OK with a null body.
        mockMvc.perform(get("/api/v1/users/profile"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }


    @Test
    void whenHealthCheck_thenReturnsOk() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/users/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("User Service is up and running!"));
    }
}