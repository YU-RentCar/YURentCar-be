package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CarSize implements EnumValue<Integer> {
    SUBCOMPACT("소형", 1), COMPACT("준중형",2), MIDSIZE("중형", 4), FULLSIZE("대형", 8);

    private final String desc;
    private final Integer dbValue;

}
