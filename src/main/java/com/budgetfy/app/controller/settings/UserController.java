package com.budgetfy.app.controller.settings;

import com.budgetfy.app.config.swagger.OpenApi;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.service.impl.LogoutService;
import com.budgetfy.app.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/settings/user")
@SecurityRequirement(name = OpenApi.BEARER)
public class UserController {

    private final UserServiceImpl userService;
    private final LogoutService logoutService;

    @GetMapping("{userId}")
    public ResponseEntity<ApiResponse> get(@PathVariable Integer userId) {
        ApiResponse response = userService.getDataById(userId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logoutService.logout(request, response, authentication);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer userId) {
        ApiResponse response = userService.delete(userId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{resetPassword}")
    public ResponseEntity<?> resetPassword(@PathVariable String resetPassword) {
        if (!checkPasswordLength(resetPassword))
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);

        userService.changePassword(resetPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> get(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "50") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        ApiResponse response = userService.getUsers(pageNo, pageSize, sortBy);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    private boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= 4 &&
                password.length() <= 16;
    }

}
