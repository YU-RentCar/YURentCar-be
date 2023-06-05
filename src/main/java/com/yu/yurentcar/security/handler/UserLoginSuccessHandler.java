package com.yu.yurentcar.security.handler;

import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import com.yu.yurentcar.global.utils.CookieProvider;
import com.yu.yurentcar.global.utils.TokenProvider;
import com.yu.yurentcar.security.dto.LoginCookies;
import com.yu.yurentcar.security.dto.TokenDto;
import com.yu.yurentcar.security.service.TokenRedisService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final CookieProvider cookieProvider;
    private final TokenRedisService tokenRedisService;

    @Value("${my.web.base-url}")
    private final String homePage;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("---------------------------");
        log.info("onAuthenticationSuccess ");

        UserAuthDto userAuth = (UserAuthDto) authentication.getPrincipal();
        log.info("login user : " + userAuth);
        log.info("authentication : " + authentication);

        try {
            // TODO : token 담아서 전송하기
            TokenDto token = tokenProvider.generateTokenDto(authentication);
            tokenRedisService.saveToken(token);

            LoginCookies loginCookies = cookieProvider.makeLoginCookies(token);

            response.addHeader(HttpHeaders.SET_COOKIE, loginCookies.getAccessCookie().toString());
            response.addHeader(HttpHeaders.SET_COOKIE, loginCookies.getRefreshCookie().toString());

            log.info("token : " + token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("end LoginSuccessHandler");

        redirectStrategy.sendRedirect(request, response, homePage);

    }
}
