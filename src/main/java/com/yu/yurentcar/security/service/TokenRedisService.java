package com.yu.yurentcar.security.service;

import com.yu.yurentcar.global.utils.TokenProvider;
import com.yu.yurentcar.security.dto.TokenDto;
import com.yu.yurentcar.security.entity.Token;
import com.yu.yurentcar.security.repository.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class TokenRedisService {
    private final TokenRedisRepository tokenRedisRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public void saveToken(TokenDto tokenDto) {
        try {
            tokenRedisRepository.save(Token.builder()
                            .username(tokenDto.getUsername())
                            .refreshToken(tokenDto.getRefreshToken())
                            .expiration(tokenDto.getAccessExpiration())
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public String getToken(String username) {
        try {
            return tokenRedisRepository.findById(username)
                    .orElseThrow(IllegalAccessError::new)
                    .getRefreshToken();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Transactional
    public void deleteToken(Token token) {
        try {
            tokenRedisRepository.delete(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Transactional
    public void deleteToken(String tokenString) {
        try {
            String username = tokenProvider.parseName(tokenString);
            Optional<Token> token = tokenRedisRepository.findById(username);
            token.ifPresent(this::deleteToken);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
