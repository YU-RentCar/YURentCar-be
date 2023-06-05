package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DriverLicense implements EnumValue<String> {
    Class1General("1종 보통"), Class2General("2종 보통"),
    Class2Small("2종 소형"), Large("1종 대형"), Special("1종 특수"),
    Bicycle("원동기 자동차 면허");

    private final String desc;

    @Override
    public String getDbValue() {
        return this.desc;
    }

}
