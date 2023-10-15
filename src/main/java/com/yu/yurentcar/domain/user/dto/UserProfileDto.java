package com.yu.yurentcar.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class UserProfileDto {
    private String username;
    private String name;
    private String nickname;
    private String phoneNumber;
    private LocalDateTime birthdate;

    @Builder
    public UserProfileDto(String username, String name, String nickname, String phoneNumber, LocalDateTime birthdate ) {
        this.username = username;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
    }
}
