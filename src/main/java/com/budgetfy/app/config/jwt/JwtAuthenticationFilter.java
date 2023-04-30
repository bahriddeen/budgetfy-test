package com.budgetfy.app.config.jwt;

import com.budgetfy.app.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Get the "Authorization" header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // If the "Authorization" header is null or doesn't start with "Bearer ", pass the request to the next filter in the chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT from the "Authorization" header
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        // If the username is not null and there is no current authentication context, proceed to check if the token is valid
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the user details by username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Check if the token is valid and not expired or revoked
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            // If the token is valid, create and set the authentication token in the security context
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }

        // Pass the request to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
