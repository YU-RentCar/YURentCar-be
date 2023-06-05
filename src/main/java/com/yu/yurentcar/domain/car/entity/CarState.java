package com.yu.yurentcar.domain.car.entity;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarState implements EnumValue<Integer> {
    // 양수 여부로 가용성 체크하기 위해서
    USABLE("사용가능", 0), REPAIR("수리/점검중", 1), THEFT("도난", 2);

    private final String desc;
    private final Integer dbValue;

}
