package com.yu.yurentcar.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ChangeNicknameDto {
    private String nickname;

    @Builder
    public ChangeNicknameDto(String nickname) {
        this.nickname = nickname;
    }
}
