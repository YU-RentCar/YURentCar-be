package com.yu.yurentcar.domain.car.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarBrand {
    HYUNDAI("현대", true), KIA("기아", true), GENESIS("제네시스", true), SAMSUNG("르노삼성", true), KGMOBOILITY("쌍용", true),
    BMW("BMW", false), BENZ("벤츠", false), VOLVO("볼보", false), AUDI("아우디", false), HONDA("혼다", false);

    private final String desc;
    private final Boolean isKorean;
}
