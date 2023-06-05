package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Transmission implements EnumValue<Boolean> {
    MANUAL("수동", false), AUTOMATIC("자동", true);

    private final String desc;
    private final Boolean isAutomatic;

    @Override
    public Boolean getDbValue() {
        return isAutomatic;
    }
}
