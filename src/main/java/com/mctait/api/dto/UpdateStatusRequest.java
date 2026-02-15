package com.mctait.api.dto;

/**
 * DTO for processing Status updates in Restful manner
 */
public class UpdateStatusRequest {

    private String status;

    public UpdateStatusRequest() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
