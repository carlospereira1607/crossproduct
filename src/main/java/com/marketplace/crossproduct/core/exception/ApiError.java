package com.marketplace.crossproduct.core.exception;

import lombok.Data;

@Data
public class ApiError {

    private String code;
    private String message;
    private int status;

    public ApiError(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
