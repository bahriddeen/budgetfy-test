package com.budgetfy.app.config.security;

import com.budgetfy.app.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security.
 * <p>
 * Defines security configurations for the BudgetFy application, including
 * HTTP security, authentication, authorization, and session management.
 * <p>
 * Also defines the security filter chain with a JWT authentication filter and
 * a logout handler, and sets up Spring Security to use the provided
 * authentication provider.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final String[] openApi = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/auth/**"
    };

    /**
     * Defines the security filter chain for the application.
     * <p>
     * Disables CSRF protection, permits access to the Swagger UI and authentication endpoints,
     * and requires authentication for all other requests. Configures session management
     * to use stateless sessions.
     * <p>
     * Also sets up the authentication provider and adds the JWT authentication filter and
     * logout handler to the filter chain.
     *
     * @param http the HTTP security object to configure
     * @return the configured security filter chain
     * @throws Exception if there is an error configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .cors().disable()
                .authorizeHttpRequests()
                .requestMatchers(openApi)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}