package com.budgetfy.app.controller.settings;

import com.budgetfy.app.config.swagger.OpenApi;
import com.budgetfy.app.payload.dto.TemplateDTO;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.service.impl.TemplateServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/settings/template")
@SecurityRequirement(name = OpenApi.BEARER)
public class TemplateController {

    private final TemplateServiceImpl templateService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody TemplateDTO templateDTO) {
        ApiResponse response = templateService.create(templateDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("{templateId}")
    public ResponseEntity<ApiResponse> get(@PathVariable Integer templateId) {
        ApiResponse response = templateService.getDataById(templateId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("{templateId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer templateId, @Valid @RequestBody TemplateDTO templateDTO) {
        ApiResponse response = templateService.update(templateId, templateDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{templateId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer templateId) {
        ApiResponse response = templateService.delete(templateId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<ApiResponse> getUserTemplates(@PathVariable Integer userId) {
        ApiResponse response = templateService.getUserTemplates(userId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<ApiResponse> searchTemplate(@PathVariable String name) {
        ApiResponse response = templateService.searchByName(name);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
