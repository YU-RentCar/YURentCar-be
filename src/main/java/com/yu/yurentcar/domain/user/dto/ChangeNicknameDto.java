package com.yu.yurentcar.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChangeNicknameDto {
    private String nickname;

    @Builder
    public ChangeNicknameDto(String nickname) {
        this.nickname = nickname;
    }
}
