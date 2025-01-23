package com.example.server.model.auth;

import com.example.server.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final User principal;

    public JwtAuthenticationToken(User principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public User getPrincipal() {
        return principal;
    }

}
