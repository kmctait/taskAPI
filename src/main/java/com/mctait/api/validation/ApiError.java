package com.mctait.api.validation;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {

    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final List<String> errors;

    public ApiError(int status, String message, List<String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    // getters

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}

