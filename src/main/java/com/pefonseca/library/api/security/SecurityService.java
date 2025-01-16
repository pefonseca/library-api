package com.pefonseca.library.api.security;

import com.pefonseca.library.api.model.AuthUser;
import com.pefonseca.library.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public AuthUser findUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomAuthentication customAuthentication) {
            return customAuthentication.getAuthUser();
        }

        return null;
    }

}
