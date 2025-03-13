package com.example.vuestargram.service;

import com.example.vuestargram.dto.request.LoginRequestDTO;
import com.example.vuestargram.dto.response.ResponseLogin;
import com.example.vuestargram.model.User;
import com.example.vuestargram.repogitory.UserRepogitory;
import com.example.vuestargram.util.CookieUtil;
import com.example.vuestargram.util.jwt.JwtUtil;
import com.example.vuestargram.util.jwt.config.JwtConfig;
import io.jsonwebtoken.security.Password;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepogitory userRepogitory;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtil cookieUtil;
    private final JwtConfig jwtConfig;

    public ResponseLogin login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        Optional<User> result = userRepogitory.findByAccount(loginRequestDTO.getAccount());
        // findByAccount : 해당 컬럼을 불러옴

        // 유저 존재 체크
        if(result.isEmpty()) {
            throw new RuntimeException("존재하지 않는 유저 있니다");
            // 로직상에서 예외처리 하고 싶을 때 = runtimeException 사용

        }

        // 비밀번호 체크
        if(!(passwordEncoder.matches(loginRequestDTO.getPassword(), result.get().getPassword()))) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        //토큰 생성
        String accessToken = jwtUtil.generateAccessToken(result.get());
        String refreshToken = jwtUtil.generatieRefreshToken(result.get());

        //리프레쉬 토큰 쿠키에 저장
        cookieUtil.setCookie(
                response
                ,jwtConfig.getRefreshTokenCookieName()
                ,refreshToken
                ,jwtConfig.getRefreshTokenCookieExpiry()
                ,jwtConfig.getReissUri() // 도메인
        );

//        return accessToken + " || " + refreshToken;
        return ResponseLogin.builder()
                .access_token(accessToken)
                .userId(result.get().getUserId()) //옵셔널 객체라서 가능
                .account(result.get().getAccount())
                .profile(result.get().getProfile())
                .name(result.get().getName())
                .build();
    }
}
