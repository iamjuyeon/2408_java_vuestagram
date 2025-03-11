package com.example.vuestargram.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Locale.filter;


@Component
public class CookieUtil {
    // Request Header 에서 특정 쿠키를 획득하는 메소드
    public Cookie getCookie(HttpServletRequest request, String name) {
        // 쿠키를 string 배열로 반환
        // 타입이 객체일때만 null이 허용됨 (php만 다름)

        // 쿠기가 없는 경우도 있으므로 optional.ofNullable을 사용해서 null를 가질 수 있는 optional를 생성
        return Arrays.stream(Optional.ofNullable(request.getCookies())
                     .orElseThrow(() -> new RuntimeException("Cookie를 찾을 수 없습니다")))
        // orElseThrow = try - catch로 에러를 잡는 과정을 축약
        // 이 과정을 거치면 Stream<Cookie[]> 생성
                    .filter(item -> item.getName().equals(name)) //필터 조건에 맞는 stream를 불러옴
                    .findFirst() // 필터 조건에 맞는 첫번째 아이템을 선택해서 optional를 return(최종 연산자)
                    .orElseThrow(() -> new RuntimeException("쿠키를 찾을 수 없습니다."));
    }

    // response header 에 쿠키 설정 메소드
    // return 타입이 없어서 voic로
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
        // 모든 쿠키를 다 가지고 있으면 낭비
        // 특정 요청만 쿠키만 확인할려면 domain 까지 같이 관리
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(domain); // 특정 요청으로 들어올때만 쿠키를 넘겨주도록 설정
        cookie.setMaxAge(maxAge); // 만료 시간 설정
        cookie.setHttpOnly(true); // 보안 쿠기 설정, 프론트에서 js 쿠키 획득이불가능
    }
}
