package com.tinder.tinder.controller;

import com.tinder.tinder.dto.request.RegisterRequest;
import com.tinder.tinder.dto.response.ApiResponse;
import com.tinder.tinder.jwt.JwtUtil;
import com.tinder.tinder.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
}
