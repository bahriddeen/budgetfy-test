package com.budgetfy.app.utils;

import com.budgetfy.app.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public final class Utils {

    /**
     * Returns an Optional<String> representing the current authenticated user's username,
     * or an empty Optional if the user is not authenticated.
     */
    public static Optional<String> getCurrentAuthenticatedUsername() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .flatMap(Utils::extractUsername);

    }

    /**
     * Private helper method that extracts the username from an Authentication object.
     * Returns an Optional<String> representing the username, or throws an IllegalArgumentException
     * if the Authentication object is not of a recognized type.
     */
    private static Optional<String> extractUsername(Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return Optional.of(((UserDetails) principal).getUsername());
        } else if (principal instanceof String) {
            return Optional.of((String) principal);
        }

        throw new IllegalArgumentException("Unknown principal type: " + principal.getClass());

    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * Returns the User object of the currently authenticated user, or null if not authenticated.
     */
    public static User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }

        return null;

    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public static String getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }


}


