package com.tinder.tinder.service;

import com.tinder.tinder.dto.request.CreateInforUser;
import com.tinder.tinder.dto.request.RegisterRequest;
import com.tinder.tinder.exception.AppException;
import com.tinder.tinder.exception.ErrorException;
import com.tinder.tinder.jwt.JwtUtil;
import com.tinder.tinder.repository.UserRepository;
import com.tinder.tinder.role.RoleName;
import com.tinder.tinder.service.impl.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.Optional;
import com.tinder.tinder.model.Users;
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.request = request;
    }
    @Override
    public void createUser(RegisterRequest request) {
        Optional<Users> userOptional = Optional.ofNullable((userRepository.findByUsername(request.getUsername())));
        if(userOptional.isPresent()) {
            throw new AppException(ErrorException.USERNAME_IS_EXISTED);
        }
        if(request.getPassword().compareTo(request.getConfirmPassword()) != 0){
            throw new AppException(ErrorException.CONFIRM_PASS_NOT_VALID);
        }
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(RoleName.ROLE_USER);
        userRepository.save(user);
    }

    @Override
    public void updateInforUser(CreateInforUser inforUser) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = jwtUtil.getUserIdFromToken(token);
            Optional<Users> userOptional = userRepository.findById(userId);
            if(userOptional.isPresent()) {
                Users user = userOptional.get();
                user.setFullName(inforUser.getFullName());
                user.setEmail(inforUser.getEmail());
                user.setAddressLat(inforUser.getAddressLat());
                user.setAddressLon(inforUser.getAddressLon());
                user.setBirthday(inforUser.getBirthday());
                user.setGender(inforUser.getGender());
                user.setInterestedIn(inforUser.getInterestedIn());
                userRepository.save(user);
            } else {
                throw new AppException(ErrorException.USER_NOT_EXIST);
            }
        }
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return (UserDetails) userRepository.findByUsername(username);
//    }
}
