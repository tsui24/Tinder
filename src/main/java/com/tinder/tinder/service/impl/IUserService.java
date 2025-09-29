package com.tinder.tinder.service.impl;

import com.tinder.tinder.dto.request.CreateInforUser;
import com.tinder.tinder.dto.request.RegisterRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService {
    void createUser(RegisterRequest user);
    void updateInforUser(CreateInforUser inforUser);
    void updateAddressUser(String addressLat, String addressLon);
    void changePassword(String oldPassword, String newPassword);
}
