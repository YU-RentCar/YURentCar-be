package com.yu.yurentcar.domain.branch.entity;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParkingSpotType implements EnumValue<Integer> {
    SIDEWALK("인도", 1),
    DRIVEWAY("차도",2),
    PARKINGSPOT_POSSIBLE("주차 가능",3),
    PARKINGSPOT_IMPOSSIBLE("주차 불가능",4);

    private final String desc;
    private final Integer dbValue;
    }
