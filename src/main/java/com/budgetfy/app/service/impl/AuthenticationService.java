package com.budgetfy.app.service.impl;

import com.budgetfy.app.config.jwt.JwtService;
import com.budgetfy.app.enums.RoleType;
import com.budgetfy.app.model.User;
import com.budgetfy.app.model.base.Token;
import com.budgetfy.app.payload.AuthenticationResponse;
import com.budgetfy.app.payload.LoginRequest;
import com.budgetfy.app.payload.RegisterRequest;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.repository.RoleRepository;
import com.budgetfy.app.repository.TokenRepository;
import com.budgetfy.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.budgetfy.app.enums.Message.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ApiResponse signup(RegisterRequest request) {

        boolean userAlreadyExist = userRepository.existsByEmail(request.getEmail());

        if (userAlreadyExist)
            return ApiResponse.error(
                    USER_ALREADY_EXISTS.message() + request.getEmail(), HttpStatus.FORBIDDEN.value()
            );

        var user = User
                .builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.findByRole(RoleType.ROLE_USER))
                .build();

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        return ApiResponse.success(
                AuthenticationResponse
                        .builder()
                        .token(jwtToken)
                        .build(), HttpStatus.CREATED.value()
        );

    }

    public ApiResponse login(LoginRequest request) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
            );

            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);

            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return ApiResponse.success(
                    AuthenticationResponse
                            .builder()
                            .token(jwtToken)
                            .build(), HttpStatus.OK.value()
            );

        } catch (Exception e) {

            return ApiResponse.error(
                    "User not found or something else, here is an error: " + e.getMessage(), HttpStatus.CONFLICT.value()
            );

        }

    }

    private void saveUserToken(User user, String jwtToken) {

        var token = Token
                .builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);

    }

    private void revokeAllUserTokens(User user) {

        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);

    }

}
