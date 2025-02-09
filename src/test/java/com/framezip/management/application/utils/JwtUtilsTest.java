package com.framezip.management.application.utils;

import com.framezip.management.application.core.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtUtilsTest {

    @Mock
    private Jwt jwt;

    @Mock
    private JwtAuthenticationToken jwtAuthenticationToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUser() {
        when(jwt.getClaimAsString("sub")).thenReturn("12345");
        when(jwt.getClaimAsString("preferred_username")).thenReturn("testuser");
        when(jwt.getClaimAsString("email")).thenReturn("testuser@example.com");
        when(jwtAuthenticationToken.getToken()).thenReturn(jwt);

        User user = JwtUtils.getUser(jwtAuthenticationToken);

        assertNotNull(user);
        assertEquals("12345", user.getId());
        assertEquals("testuser", user.getName());
        assertEquals("testuser@example.com", user.getEmail());
    }
}