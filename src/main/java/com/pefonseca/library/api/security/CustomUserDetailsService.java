package com.pefonseca.library.api.security;

import com.pefonseca.library.api.model.AuthUser;
import com.pefonseca.library.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = service.findByLogin(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                   .username(user.getLogin())
                   .password(user.getPassword())
                   .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
                   .build();
    }
}
