package com.pefonseca.library.api.security;

import com.pefonseca.library.api.model.AuthUser;
import com.pefonseca.library.api.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    private static final String PASSWORD_DEFAULT = "321";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = authenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        AuthUser authUser = userService.findByEmail(email);

        if(authUser == null) {
            authUser = saveNewUser(email);
        }

        authentication = new CustomAuthentication(authUser);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private AuthUser saveNewUser(String email) {
        AuthUser authUser;
        authUser = new AuthUser();
        authUser.setEmail(email);
        authUser.setLogin(getLoginFromEmail(email));
        authUser.setPassword(PASSWORD_DEFAULT);
        authUser.setRoles(List.of("OPERADOR"));
        userService.save(authUser);
        return authUser;
    }

    private String getLoginFromEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
