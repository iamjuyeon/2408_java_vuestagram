package com.example.vuestargram.util.jwt;

import com.example.vuestargram.model.User;
import com.example.vuestargram.util.jwt.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final JwtConfig jwtconfig;
    private final SecretKey secretKey;

    public JwtUtil(JwtConfig jwtconfig) {
        this.jwtconfig = jwtconfig;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtconfig.getSecret()));
    }
    // 엑세스 토큰 생성 메소드
    public String generateAccessToken(User user) {
        // 파라미터(유저아이디, 토큰 유효시간)
        return this.generateToken(user.getUserId(), jwtconfig.getAccessTokenExpiry());
    }

    //리프레시 토큰 생성 메소드
    public String generatieRefreshToken(User user) {
        return this.generateToken(user.getUserId(), jwtconfig.getRefreshTokenExpiry());
    }

    //jjwt 경우 header와 payload, secretKey를 셋팅하면 signature는 자동으로 생성해줌
    // 토근 생성 메소드
    public String generateToken(Long userId, int expiry) {
        //ttl : time to limit
        Date now = new Date();
        // jjwt 에서 기본적으로 쓸수있게 되어있음
        return Jwts.builder() //  jwt 빌더 객체 생성
                .header().type(jwtconfig.getType())
                .and()
                .setSubject(String.valueOf(userId)) // pk, id 둘중하나 넣어줌
                .setIssuer(jwtconfig.getIssuer()) // payload.iss 셋 토큰 발급자
                .setIssuedAt(now) //payload.iat 셋(토큰 발급 시간)
                .setExpiration(new Date(now.getTime() + expiry)) //만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) //secret 키 생성
                .compact(); //토큰 생성
    }
}
