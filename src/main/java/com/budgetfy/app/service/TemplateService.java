package com.budgetfy.app.service;

import com.budgetfy.app.payload.response.ApiResponse;

public interface TemplateService {
    ApiResponse getUserTemplates(Integer userId);

    ApiResponse searchByName(String templateName);
}
