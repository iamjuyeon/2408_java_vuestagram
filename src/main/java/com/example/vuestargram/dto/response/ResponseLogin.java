package com.example.vuestargram.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseLogin {
    private String access_token;
    private Long userId;
    private String name;
    private String account;
    private String profile;
}
