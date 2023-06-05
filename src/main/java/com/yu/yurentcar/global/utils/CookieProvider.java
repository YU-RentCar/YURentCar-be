package com.yu.yurentcar.global.utils;

import com.yu.yurentcar.security.dto.LoginCookies;
import com.yu.yurentcar.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CookieProvider {

    @Value("${my.web.domain}")
    private String DOMAIN;

    public LoginCookies makeLoginCookies(TokenDto tokenDto) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken",tokenDto.getAccessToken())
                .httpOnly(true)
                .path("/")
                .maxAge(tokenDto.getAccessExpiration())
                .domain(DOMAIN)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken",tokenDto.getRefreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(tokenDto.getRefreshExpiration())
                .domain(DOMAIN)
                .build();

        return LoginCookies.builder()
                .accessCookie(accessCookie)
                .refreshCookie(refreshCookie)
                .build();

    }
}
