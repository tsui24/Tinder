package com.tinder.tinder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AppException extends RuntimeException {
    public ErrorException errorException;

    public ErrorException getErrorException() {
        return errorException;
    }

    public AppException(ErrorException errorException) {
    }

    public AppException() {
    }
}
