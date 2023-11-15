package com.yu.yurentcar.security.filter;

import com.yu.yurentcar.domain.user.service.CustomAdminDetailsService;
import com.yu.yurentcar.domain.user.service.CustomUserDetailsService;
import com.yu.yurentcar.global.utils.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAdminDetailsService customAdminDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);

        log.info("=======JwtFilter======");
        log.info("jwt : " + jwt);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            UserDetails userDetails = null;
            User user = (User)authentication.getPrincipal();
            System.out.println("authorities: " + user.getAuthorities());
            if(user.getAuthorities().contains(new SimpleGrantedAuthority("USER_ROLE")))
                userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
            else
                userDetails = customAdminDetailsService.loadUserByUsername(user.getUsername());
            Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            System.out.println("details: " + newAuth);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            log.info("cookies is null");
            return null;
        }

        Optional<Cookie> accessCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("accessToken")).findAny();

        if(accessCookie.isPresent()) {
            return accessCookie.get().getValue();
        }

        log.info("access cookie is null");
        return null;
        // TODO : 해당 로직 검증 필요 -> 리프레시 토큰을 넘겨주는 것이 맞나??
        /*
        Optional<Cookie> refreshCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("refreshToken")).findAny();

        return refreshCookie.orElse(null).getValue();
         */

    }

    /*
    // header로 통신하는 방법 -> 현재 미적용
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
     */
}
