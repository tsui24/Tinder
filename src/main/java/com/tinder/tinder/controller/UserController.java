package com.tinder.tinder.controller;

import com.tinder.tinder.dto.request.CreateInforUser;
import com.tinder.tinder.dto.request.RegisterRequest;
import com.tinder.tinder.dto.response.ApiResponse;
import com.tinder.tinder.jwt.JwtUtil;
import com.tinder.tinder.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
//    @GetMapping("/me")
//    public ApiResponse<AccountResponse> infor(HttpServletRequest request) {
//        ApiResponse apiResponse = new ApiResponse();
//        String authHeader = request.getHeader("Authorization");
//        if(authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            Long userId = jwtUtil.getUserIdFromToken(token);
//            AccountResponse accountResponse = userService.getAccountInfor(userId);
//            if(accountResponse != null) {
//                apiResponse.setCode(200);
//                apiResponse.setMessage("Ok");
//                apiResponse.setResult(accountResponse);
//            }
//        }
//        return apiResponse;
//    }
//    @GetMapping("/get-id")
//    public ResponseEntity<?> getUserId(@RequestHeader("Authorization") String authHeader) {
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("code", 401, "message", "Token không hợp lệ hoặc thiếu"));
//        }
//        try {
//            String token = authHeader.substring(7);
//            System.out.println(token);
//            Long userId = jwtUtil.getUserIdFromToken(token);
//            System.out.println(userId);
//            return ResponseEntity.ok(Map.of("userId", userId));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("code", 401, "message", "Token không hợp lệ hoặc đã hết hạn"));
//        }
//    }
    @PutMapping("create-infor-user")
    public ApiResponse<String> createUser(@RequestBody @Valid CreateInforUser inforUser) {
        ApiResponse apiResponse = new ApiResponse();
        userService.updateInforUser(inforUser);
        apiResponse.setMessage("Tạo thông tin người dùng thành công");
        apiResponse.setCode(200);
        apiResponse.setResult("");
        return apiResponse;
    }
    @PutMapping("update-address")
    public ApiResponse<String> updateAddress(@RequestParam String addressLon, String addressLat) {
        ApiResponse apiResponse = new ApiResponse();
        userService.updateAddressUser(addressLon, addressLat);
        apiResponse.setMessage("Cập nhật vị trí của người dùng thành công");
        apiResponse.setCode(200);
        apiResponse.setResult("");
        return apiResponse;
    }

    @PutMapping("update-password")
    public ApiResponse<String> updatePassword(@RequestParam String oldPassword, String newPassword) {
        ApiResponse apiResponse = new ApiResponse();
        userService.changePassword(oldPassword, newPassword);
        apiResponse.setMessage("Đổi mật khẩu thành công");
        apiResponse.setCode(200);
        apiResponse.setResult("");
        return apiResponse;
    }

    @GetMapping("check-user")
    public ApiResponse<Boolean> checkUser() {
        ApiResponse response = new ApiResponse<>();
        Boolean result = userService.checkUser();
        response.setCode(200);
        response.setResult(result);
        return response;
    }
}
