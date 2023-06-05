package com.yu.yurentcar.global.utils;

import com.yu.yurentcar.security.dto.TokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_EXPIRE = 1000 * 60 * 10;
    public static long REFRESH_EXPIRE = 1000 * 60 * 60 * 24;

    @Value("${jwt.secret-key}")
    private String secretKey;

    /*
     *
     * refreshToken
     * 유효기간 : 24시간
     * subject : userId
     * claims : [nickname]
     *
     * accessToken
     * 유효기간 : 10분
     * subject : userId
     * claims : [null]
     *
     *
     *
     * */


    public TokenDto generateTokenDto(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        log.info("========start generate token========");
        log.info("authorities : " + authorities);

        long now = new Date().getTime();

        //refresh 생성
        Date accessExpiration = new Date(now + ACCESS_EXPIRE);
        String accessToken = Jwts.builder()
                .setExpiration(accessExpiration)
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();

        Date refreshExpiration = new Date(now + REFRESH_EXPIRE);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshExpiration)
                .setSubject(authentication.getName())
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();

        log.info("access : " + accessToken);
        log.info("refresh : " + refreshToken);

        TokenDto tokenDto = TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpiration(accessExpiration.getTime())
                .refreshExpiration(refreshExpiration.getTime())
                .username(authentication.getName())
                .build();

        log.info("tokenDto : " + tokenDto);

        return tokenDto;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }

    public String parseName(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(accessToken).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }


    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
