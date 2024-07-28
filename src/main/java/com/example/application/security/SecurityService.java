package com.example.application.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityService {

    private final AuthenticationContext authenticationContext;

    public SecurityService(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public UserDetails getAuthenticatedUser() {
        Optional<UserDetails> optionalUserDetails = authenticationContext.getAuthenticatedUser(UserDetails.class);
        return optionalUserDetails.isEmpty() ? null : optionalUserDetails.get();
    }

    public void logout() {
        authenticationContext.logout();
    }
}