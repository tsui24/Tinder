package com.tinder.tinder.Utils;

import com.tinder.tinder.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class UtilsService {
    private final HttpServletRequest request;
    private final JwtUtil jwtUtil;
    public UtilsService(HttpServletRequest request,JwtUtil jwtUtil) {
        this.request = request;
        this.jwtUtil = jwtUtil;
    }
    public Long getUserIdFromToken() {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = jwtUtil.getUserIdFromToken(token);
            return userId;
        }
        return (long) 0;
    }
}
