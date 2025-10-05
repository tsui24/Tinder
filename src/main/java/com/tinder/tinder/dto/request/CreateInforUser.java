package com.tinder.tinder.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateInforUser {
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 100, message = "Họ và tên không được vượt quá 100 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Kinh độ không được để trống")
    private String addressLon;

    @NotBlank(message = "Vĩ độ không được để trống")
    private String addressLat;

    @NotNull(message = "Giới tính là bắt buộc")
    @Min(value = 0, message = "Giới tính phải là Nam hoặc Nữ hoặc Khác")
    @Max(value = 2, message = "Giới tính phải là Nam hoặc Nữ hoặc Khác")
    private Integer gender;

    @NotNull(message = "Đối tượng quan tâm là bắt buộc")
    @Min(value = 0, message = "Đối tượng quan tâm phải là Nam hoặc Nữ hoặc Cả 2")
    @Max(value = 2, message = "Đối tượng quan tâm phải là Nam hoặc Nữ hoặc Cả 2")
    private Integer interestedIn;

    @NotNull(message = "Ngày sinh là bắt buộc")
    @Past(message = "Ngày sinh phải ở trong quá khứ")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    private List<String> images = new ArrayList<>();

    public CreateInforUser() {
    }

    public CreateInforUser(String fullName, String email, String addressLon, String addressLat, Integer gender, Integer interestedIn, LocalDate birthday) {
        this.fullName = fullName;
        this.email = email;
        this.addressLon = addressLon;
        this.addressLat = addressLat;
        this.gender = gender;
        this.interestedIn = interestedIn;
        this.birthday = birthday;
    }

    public @NotBlank(message = "Họ và tên không được để trống") @Size(max = 100, message = "Họ và tên không được vượt quá 100 ký tự") String getFullName() {
        return fullName;
    }

    public void setFullName(@NotBlank(message = "Họ và tên không được để trống") @Size(max = 100, message = "Họ và tên không được vượt quá 100 ký tự") String fullName) {
        this.fullName = fullName;
    }

    public @NotBlank(message = "Email không được để trống") @Email(message = "Email không hợp lệ") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email không được để trống") @Email(message = "Email không hợp lệ") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Kinh độ không được để trống") String getAddressLon() {
        return addressLon;
    }

    public void setAddressLon(@NotBlank(message = "Kinh độ không được để trống") String addressLon) {
        this.addressLon = addressLon;
    }

    public @NotBlank(message = "Vĩ độ không được để trống") String getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(@NotBlank(message = "Vĩ độ không được để trống") String addressLat) {
        this.addressLat = addressLat;
    }

    public @NotNull(message = "Giới tính là bắt buộc") @Min(value = 0, message = "Giới tính phải là Nam hoặc Nữ hoặc Khác") @Max(value = 2, message = "Giới tính phải là Nam hoặc Nữ hoặc Khác") Integer getGender() {
        return gender;
    }

    public void setGender(@NotNull(message = "Giới tính là bắt buộc") @Min(value = 0, message = "Giới tính phải là Nam hoặc Nữ hoặc Khác") @Max(value = 2, message = "Giới tính phải là Nam hoặc Nữ hoặc Khác") Integer gender) {
        this.gender = gender;
    }

    public @NotNull(message = "Đối tượng quan tâm là bắt buộc") @Min(value = 0, message = "Đối tượng quan tâm phải là Nam hoặc Nữ hoặc Cả 2") @Max(value = 2, message = "Đối tượng quan tâm phải là Nam hoặc Nữ hoặc Cả 2") Integer getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(@NotNull(message = "Đối tượng quan tâm là bắt buộc") @Min(value = 0, message = "Đối tượng quan tâm phải là Nam hoặc Nữ hoặc Cả 2") @Max(value = 2, message = "Đối tượng quan tâm phải là Nam hoặc Nữ hoặc Cả 2") Integer interestedIn) {
        this.interestedIn = interestedIn;
    }

    public @NotNull(message = "Ngày sinh là bắt buộc") @Past(message = "Ngày sinh phải ở trong quá khứ") LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(@NotNull(message = "Ngày sinh là bắt buộc") @Past(message = "Ngày sinh phải ở trong quá khứ") LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public CreateInforUser(String fullName, String email, String addressLon, String addressLat, Integer gender, Integer interestedIn, LocalDate birthday, List<String> images) {
        this.fullName = fullName;
        this.email = email;
        this.addressLon = addressLon;
        this.addressLat = addressLat;
        this.gender = gender;
        this.interestedIn = interestedIn;
        this.birthday = birthday;
        this.images = images;
    }
}
