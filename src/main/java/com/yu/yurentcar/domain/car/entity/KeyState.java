package com.yu.yurentcar.domain.car.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeyState {
    MASTER("마스터키", 1),USABLE("사용중", 2), WAITING("여분키", 3), THEFT("도난", 4);

    private final String desc;
    private final Integer dbValue;
}
