package com.framezip.management.application.utils;

import com.framezip.management.application.core.domain.User;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtUtils {

    public static User getUser(JwtAuthenticationToken auth) {
        return User.builder()
                .id(auth.getToken().getClaimAsString(StandardClaimNames.SUB))
                .name(auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME))
                .email(auth.getToken().getClaimAsString(StandardClaimNames.EMAIL))
                .build();
    }
}
