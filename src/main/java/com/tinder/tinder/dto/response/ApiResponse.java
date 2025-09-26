package com.tinder.tinder.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tinder.tinder.exception.ErrorException;
import lombok.*;
import lombok.experimental.FieldDefaults;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    int code;
    String message;
    T result;

    public ApiResponse(ErrorException errorException) {
        this.code = errorException.getCode();
        this.message = errorException.getMessage();
        this.result = null;
    }

    public ApiResponse() {
    }

    public ApiResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
