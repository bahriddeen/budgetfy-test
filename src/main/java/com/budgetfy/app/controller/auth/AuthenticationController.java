package com.budgetfy.app.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.budgetfy.app.payload.LoginRequest;
import org.springframework.http.ResponseEntity;
import com.budgetfy.app.config.swagger.OpenApi;
import com.budgetfy.app.payload.RegisterRequest;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.service.impl.AuthenticationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@SecurityRequirement(name = OpenApi.BEARER)
public class AuthenticationController {

    private final AuthenticationService authService;

    /**
     * Registers a new user account.
     *
     * @param request the register request object
     * @return the response entity containing the status code and jwt token
     */
    @PostMapping("signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody RegisterRequest request) {
        ApiResponse signup = authService.signup(request);
        return ResponseEntity.status(signup.getStatus()).body(signup);
    }

    /**
     * Logs in a user with the given credentials.
     *
     * @param request the login request object
     * @return the response entity containing the status code and response body
     */
    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        ApiResponse login = authService.login(request);
        return ResponseEntity.status(login.getStatus()).body(login);
    }

}
