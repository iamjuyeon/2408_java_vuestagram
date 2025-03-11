package com.example.vuestargram.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder // 유저가 전달해올 데이터를 json 객체로
public class LoginRequestDTO {
    @NotBlank(message = "계정을 작성해주세요")
    private String account;

    @NotBlank(message = "비밀번호를 적어주세요")
    private String password;

}
