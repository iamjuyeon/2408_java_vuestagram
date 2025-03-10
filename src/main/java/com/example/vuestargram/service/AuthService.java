package com.example.vuestargram.service;

import com.example.vuestargram.model.User;
import com.example.vuestargram.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;

    public String login() {
        User user = new User();
        user.setUserId(2L);

        //토큰 생성
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generatieRefreshToken(user);

        return accessToken + " || " + refreshToken;

    }
}
