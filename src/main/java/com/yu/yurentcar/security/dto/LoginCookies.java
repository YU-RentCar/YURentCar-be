package com.yu.yurentcar.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseCookie;

@Getter
@AllArgsConstructor
@Builder
public class LoginCookies {
    private ResponseCookie accessCookie;
    private ResponseCookie refreshCookie;
}
