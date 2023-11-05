package com.yu.yurentcar.domain.branch.entity;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParkingSpotType implements EnumValue<Integer> {
    SIDEWALK("인도", 1),
    DRIVEWAY("차도",2),
    PARKINGSPOT_CAR("주차 중",3),
    PARKINGSPOT_NO_CAR("주차 가능",4),
    PARKINGSPOT_IMPOSSIBLE("주차 불가능",5);

    private final String desc;
    private final Integer dbValue;
    }
