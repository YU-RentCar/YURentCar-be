package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import lombok.Getter;

@Getter
public enum OilType implements EnumValue<Integer> {
    GASOLINE("휘발유", 1), DIESEL("경유", 2), FUELCELL("수소", 4), ELECTRIC("전기", 8);

    private final String desc;
    private final Integer dbValue;

    OilType(String desc, Integer dbValue) {
        this.desc = desc;
        this.dbValue = dbValue;
    }

}
