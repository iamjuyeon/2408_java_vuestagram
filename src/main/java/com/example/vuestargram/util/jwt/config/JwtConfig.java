package com.example.vuestargram.util.jwt.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "config.jwt") // yml과 Jwtconfig 메소드를 연결
public class JwtConfig {
    private final String issuer;
    private final String type; // 타입 : jwt
    private final int AccessTokenExpiry; //access 토큰 유효시간(msec 단위)
    private final int refreshTokenExpiry;//refresh 토큰 만료 시간
    private final String refreshTokenCookieName;
    private final int refreshTokenCookieExpiry;
    private final String secret; //암호화 사용할때 시크릿 번호
    private final String headerKey;
    private final String scheme; // barrier 을 담고 있는 프로퍼티
    private final String reissUri;

}
