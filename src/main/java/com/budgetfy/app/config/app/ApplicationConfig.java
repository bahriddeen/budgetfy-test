package com.budgetfy.app.config.app;

import lombok.RequiredArgsConstructor;
import com.budgetfy.app.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.budgetfy.app.utils.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

/**
 * Configuration class for the application. Contains bean definitions
 * for authentication-related components.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;

    /**
     * Defines a bean that returns a UserDetailsService implementation which
     * retrieves user details from UserRepository based on username.
     * Throws ResourceNotFoundException if the user is not found.
     *
     * @return UserDetailsService instance
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    /**
     * Defines a bean that returns an AuthenticationProvider implementation
     * that uses userDetailsService and BCryptPasswordEncoder to authenticate users.
     *
     * @return AuthenticationProvider instance
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Returns an AuthenticationManager instance that is used to authenticate
     * user credentials during the login process. The AuthenticationManager is
     * obtained from the AuthenticationConfiguration instance.
     *
     * @param config AuthenticationConfiguration instance
     * @return AuthenticationManager instance
     * @throws Exception if there is an error creating the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Returns a BCryptPasswordEncoder instance for password encoding.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
