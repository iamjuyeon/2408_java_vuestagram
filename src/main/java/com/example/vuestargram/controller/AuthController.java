package com.example.vuestargram.controller;

import com.example.vuestargram.dto.request.LoginRequestDTO;
import com.example.vuestargram.dto.response.ResponseBase;
import com.example.vuestargram.dto.response.ResponseLogin;
import com.example.vuestargram.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    // 더이상 필요없음(authService 만들어서)
    //private final JwtConfig jwtconfig;

    private final AuthService authService;

    @PostMapping ("/login")
    public ResponseEntity<ResponseBase<ResponseLogin>> login(
            HttpServletResponse response
            ,@Valid @RequestBody LoginRequestDTO loginRequestDTO // json데이터가 body에 담겨서 옴, 실제로 우리가 보는 데이터가 담김
    ) {
//        return loginRequestDTO.getAccount() + ":" +loginRequestDTO.getPassword();
        ResponseLogin responseLogin = authService.login(loginRequestDTO, response);
        ResponseBase<ResponseLogin> responseBase =
                ResponseBase.<ResponseLogin>builder()
                        .status(200)
                        .message("로그인 성공")
                        .data(responseLogin)
                        .build();
        return ResponseEntity.status(200).body(responseBase);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
