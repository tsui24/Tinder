package com.tinder.tinder.service.impl;

import com.tinder.tinder.dto.request.RegisterRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService {
    void createUser(RegisterRequest user);
}
