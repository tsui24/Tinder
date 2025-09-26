package com.tinder.tinder.model;

import com.tinder.tinder.role.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username không được để trống")
    @Size(min = 4, max = 20, message = "Username phải có độ dài từ 4 đến 20 ký tự")
    private String username;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 6, message = "Password phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Kinh độ không được để trống")
    private String addressLon;

    @NotBlank(message = "Vĩ độ không được để trống")
    private String addressLat;

    @NotNull(message = "Giới tính không được để trống")
    @Min(value = 0, message = "Giới tính phải >= 0")
    @Max(value = 2, message = "Giới tính phải <= 2")
    private Integer gender;

    @NotNull(message = "Phần quan tâm không được để trống")
    @Min(value = 0, message = "Giá trị phải >= 0")
    @Max(value = 2, message = "Giá trị phải <= 2")
    private Integer interestedIn;

    @Past(message = "Ngày sinh phải trong quá khứ")
    private LocalDateTime birthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName role;

    public Users() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAddressLon() {
        return addressLon;
    }

    public void setAddressLon(String addressLon) {
        this.addressLon = addressLon;
    }

    public String getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(String addressLat) {
        this.addressLat = addressLat;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(Integer interestedIn) {
        this.interestedIn = interestedIn;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}
