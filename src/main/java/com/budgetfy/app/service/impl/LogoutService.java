package com.budgetfy.app.service.impl;

import com.budgetfy.app.repository.TokenRepository;
import com.budgetfy.app.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    final Logger log = LoggerFactory.getLogger(LogoutService.class);

    /**
     * This method is used to log out a user by revoking and expiring the JWT token
     *
     * @param request        HttpServletRequest object containing the request information
     * @param response       HttpServletResponse object containing the response information
     * @param authentication Authentication object containing the authentication details
     */
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String jwt;
        final String authHeader = request.getHeader("Authorization");

        // Check if Authorization header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return;

        // Extract the JWT token from Authorization header
        jwt = authHeader.substring(7);

        // Retrieve the token from database if exists
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);

        // If token exists, revoke and expire it and clear the security context
        if (storedToken != null) {

            storedToken.setExpired(true);
            storedToken.setRevoked(true);

            tokenRepository.save(storedToken);

            // logged out
            SecurityContextHolder.clearContext();
            log.trace("User logged out: " + Utils.getCurrentUserName());

        }
    }
}
