package com.tinder.tinder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public enum ErrorException {
    PHONE_EXIST(101, "Số điện thoại đã tồn tại"),
    INVALID_KEY(102,"Invalid key"),
    STUDENT_NOT_EXIST(103, "Học sinh chưa tồn tại"),
    INVALID_FORMAT_DATE(104, "Format date không hợp lệ, hãy sử dụng dd-MM-YYYY"),
    USERNAME_IS_EXISTED(105, "Tên đăng nhập đã tồn tại"),
    CONFIRM_PASS_NOT_VALID(106, "Mật khẩu xác nhận bị sai"),
    WRONG_USERNAME_PASSWORD(107, "Sai tên đăng nhập hoặc mật khẩu"),
    SUBJECT_NOT_EXIST(108,"Môn học chưa tồn tại"),
    STUDENT_NUMBER_EXISTED(109, "Mã số sinh viên đã tồn tại"),
    STUDENT_HAVE_USER(109, "Sinh viên này đã có account đăng kí");
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
