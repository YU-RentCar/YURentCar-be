package com.yu.yurentcar.security.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("token")
public class Token {
    @Id
    private String username;
    private String refreshToken;

    @TimeToLive
    private Long expiration;

    @Builder
    public Token(String username, String refreshToken, Long expiration) {
        this.username = username;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
