package com.budgetfy.app.controller;

import com.budgetfy.app.config.swagger.OpenApi;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/analytics")
@SecurityRequirement(name = OpenApi.BEARER)
public class AnalyticsController {
}
