package com.pefonseca.library.api.security;

import com.pefonseca.library.api.model.AuthUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

    private final AuthUser authUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authUser.getRoles()
                            .stream().map(SimpleGrantedAuthority::new)
                            .toList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.authUser;
    }

    @Override
    public Object getPrincipal() {
        return this.authUser;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.authUser.getLogin();
    }
}
