package com.tinder.tinder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public enum ErrorException {
    INVALID_KEY(102,"Invalid key"),
    USER_NOT_EXIST(103, "Người dùng chưa tồn tại"),
    INVALID_FORMAT_DATE(104, "Format date không hợp lệ, hãy sử dụng dd-MM-YYYY"),
    USERNAME_IS_EXISTED(105, "Tên đăng nhập đã tồn tại"),
    CONFIRM_PASS_NOT_VALID(106, "Mật khẩu xác nhận bị sai"),
    WRONG_USERNAME_PASSWORD(107, "Sai tên đăng nhập hoặc mật khẩu"),
    INVALID_OLD_PASSWORD(108, "Mật khẩu cũ không hợp lệ"),
    NEW_PASSWORD_SAME_AS_OLD(109, "Mật khẩu mới giống với mật khẩu cũ"),
    INTEREST_NAM_NOT_BLANK(110, "Tên sở thích không được để trống"),
    NAME_INTEREST_EXISTS(111, "Tên sở thích đã tồn tại");
    private int code;
    private String message;

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

    ErrorException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ErrorException() {
    }
}
