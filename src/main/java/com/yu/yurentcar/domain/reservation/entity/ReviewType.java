package com.yu.yurentcar.domain.reservation.entity;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewType implements EnumValue<Integer> {
    POSSIBLE_POINT("후기 작성 가능/포인트 적립 가능", 1), POSSIBLE_NO_POINT("후기 작성 가능/포인트 적립 불가능", 2), IMPOSSIBLE("후기 작성 불가능", 3), ALREADY("후기 작성함", 4);

    private final String desc;
    private final Integer dbValue;
}