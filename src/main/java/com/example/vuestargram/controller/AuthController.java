package com.example.vuestargram.controller;

import com.example.vuestargram.dto.request.LoginRequestDTO;
import com.example.vuestargram.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    // 더이상 필요없음(authService 만들어서)
    //private final JwtConfig jwtconfig;

    private final AuthService authService;

    @PostMapping ("/login")
    public String login(

            @Valid @RequestBody LoginRequestDTO loginRequestDTO // json데이터가 body에 담겨서 옴, 실제로 우리가 보는 데이터가 담김
    ) {
//        return loginRequestDTO.getAccount() + ":" +loginRequestDTO.getPassword();
         return authService.login(loginRequestDTO);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
