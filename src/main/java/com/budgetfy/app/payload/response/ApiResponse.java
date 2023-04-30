package com.budgetfy.app.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ApiResponse represents the response object returned by the API endpoints.
 * It contains the status code, data, message, and success flag.
 */
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse implements Serializable {

    // Fields
    private int status;
    private Object data;
    private String message;
    private boolean success;


    // Constructors

    public ApiResponse(boolean success) {
        this.success = success;
    }

    public ApiResponse(boolean success, int status, Object data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public ApiResponse(boolean success, int status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }

    public ApiResponse(boolean success, int status, String message, Object data) {
        this(success, status, message);
        this.data = data;
    }

    // Static factory methods
    public static ApiResponse success() {
        return new ApiResponse(true);
    }

    public static ApiResponse success(Object data, int status) {
        return new ApiResponse(true, status, data);
    }

    public static ApiResponse success(String message, Object data, int status) {
        return new ApiResponse(true, status, message, data);
    }

    public static ApiResponse success(String message, int status) {
        return new ApiResponse(true, status, message);
    }

    public static ApiResponse error(String message, int status) {
        return new ApiResponse(false, status, message);
    }

}
