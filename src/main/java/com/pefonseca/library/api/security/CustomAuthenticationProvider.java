package com.pefonseca.library.api.security;

import com.pefonseca.library.api.model.AuthUser;
import com.pefonseca.library.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        AuthUser userAuthenticate = userService.findByLogin(login);

        if(userAuthenticate == null) {
            throw getUsernameNotFoundException();
        }

        String passwordEncoded = userAuthenticate.getPassword();

        boolean matchesPassword = passwordEncoder.matches(password, passwordEncoded);

        if(!matchesPassword) {
            throw getUsernameNotFoundException();
        }

        return new CustomAuthentication(userAuthenticate);
    }

    private static UsernameNotFoundException getUsernameNotFoundException() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
