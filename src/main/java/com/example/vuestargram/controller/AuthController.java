package com.example.vuestargram.controller;

import com.example.vuestargram.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    // 더이상 필요없음(authService 만들어서)
    //private final JwtConfig jwtconfig;

    private final AuthService authService;

    @PostMapping ("/login")
    public String login() {
        return authService.login();
    }

    @GetMapping("/test/")
    public String test() {
        return "test";
    }
}
