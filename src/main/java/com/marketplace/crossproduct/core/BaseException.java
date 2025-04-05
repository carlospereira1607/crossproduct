package com.marketplace.crossproduct.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class BaseException extends Exception implements Serializable {

    private Integer exceptionCode;

    BaseException(String msg) {
        super(msg);
    }

    BaseException(String msg, Integer exceptionCode) {
        super(msg);
        this.exceptionCode = exceptionCode;
    }
}
