package com.example.vuestargram.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class securityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 비활성화(토큰 사용할려고)
        //둘다 사용해서 인증이 안되면 에러 페이지로 이동
            .httpBasic(h -> h.disable()) // SSR이 아니므로 화면 생성 비활성 설정.
            .formLogin(form -> form.disable()) // SSR이 아니므로 폼로그인 기능 비활성 설정.
            .csrf(csrf -> csrf.disable()) //csrf 토큰 인증 비활성화 설정
            .authorizeHttpRequests(request ->
                    request.requestMatchers("/api/login").permitAll() // 인가 없이 접근 가능한 경로
                            .anyRequest().authenticated() //적은거 외에 나머지는 인증이 필요
                    )
            .build();
    }
}
