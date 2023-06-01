package com.yu.yurentcar.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChangeNicknameDto {
    private String username;
    private String nickname;

    @Builder
    public ChangeNicknameDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
