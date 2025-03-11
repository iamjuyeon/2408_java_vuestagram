package com.example.vuestargram.security;


import com.example.vuestargram.util.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 유저가 요청한 토큰을 가져와서 세팅을 하는 처리(필터 단계에서 처리)
// 필터로 동장
@Component
@RequiredArgsConstructor // 생성자를 통해서 객체를 생성하는데 생성자를 생성할 필요없게 함
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter : 딱 한번만 실행되는 객체

    private final JwtUtil jwtUtil;

    private final SecurityAuthenticationProvider securityAuthenticationProvider;
    //시큐리티와 관련된 인증정보를 모두 가지고 있는 객체

    // D.I.(의존성 주입)
    // 프레임워크에서 자동으로 인스턴스화 해줌

    @Override
    // 엑세스 토큰의 유효 여부를 확인하고 인증 정보를 스프링 시큐리티에 설정하는 메소드

    protected  void doFilterInternal(HttpServletRequest request
                                     , HttpServletResponse response
                                     , FilterChain filterChain) throws ServletException, IOException {

        // 쿠키에서 토큰 획득
        String token = jwtUtil.getAccessTokenInCookie(request);

        if(token != null ) {
            try {
                // 시큐리티 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(securityAuthenticationProvider.authenticate(token)); // authenticate 호출


            } catch(Exception e) {
                throw new RuntimeException("사용할 수 없는 토큰 입니다");
            }

        }
        filterChain.doFilter(request, response);
    }



}
