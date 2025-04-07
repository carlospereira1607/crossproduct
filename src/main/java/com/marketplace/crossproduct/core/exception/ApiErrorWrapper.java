package com.marketplace.crossproduct.core.exception;

import lombok.Data;

@Data
public class ApiErrorWrapper {

    private String code;
    private String message;
    private int status;

    public ApiErrorWrapper(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
