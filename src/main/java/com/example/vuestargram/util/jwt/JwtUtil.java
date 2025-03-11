package com.example.vuestargram.util.jwt;

import com.example.vuestargram.model.User;
import com.example.vuestargram.util.jwt.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final JwtConfig jwtconfig;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtconfig, JwtConfig jwtConfig) {
        this.jwtconfig = jwtconfig;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtconfig.getSecret()));
        this.jwtConfig = jwtConfig;
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

    // 페이로드(claims ) 추출 및 토큰 검증 메소드
    // 토큰 안에 있는 요소 하나하나들을 claims라고 부름(자바에서)
    public Claims getClaims(String token) {
        return Jwts.parser() // jwt 파서 객체 생성
                .verifyWith(this.secretKey) // 서명 검증을 위한 비밀키 설정
                .build() // 파서 빌드
                // 토근 유효시간 검증, 시그니처 검증, 등등 확인 하는 절차
                .parseSignedClaims(token) // jwt 파싱 및 검증
                .getPayload(); //페이로드(claims) 반환

    }

    // 쿠키에서 엑세스 토큰 획득
    public String getAccessTokenInCookie(HttpServletRequest request) {
        // request header에서 bearerToken 획득
        String bearerToken = request.getHeader(jwtConfig.getHeaderKey());

        // 토큰 존재 여부 체크 & "Bearer"로 시작하는지 체크
        // jwtConfig에 미리 만들어 둔 getScheme 사용
        if(bearerToken == null || !bearerToken.startsWith(jwtConfig.getScheme())) {
            return null; // null 하는 이유?
        }

        return bearerToken.substring(jwtConfig.getScheme().length() +1);
        // +1 : 빈칸
        // yml파일은 뒤에 빈 공간을 인식하지 못함
    }

}
