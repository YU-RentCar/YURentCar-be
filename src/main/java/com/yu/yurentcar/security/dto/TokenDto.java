package com.yu.yurentcar.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class TokenDto {
    private String grantType;
    private String username;
    private String accessToken;
    private String refreshToken;
    private Long accessExpiration;
    private Long refreshExpiration;

}
