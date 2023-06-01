package com.yu.yurentcar.domain.user.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class UserProfileDto {
    private String username;
    private String name;
    private String nickname;
    private String birthday;
    private String phoneNumber;
}
