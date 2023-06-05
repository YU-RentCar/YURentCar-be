package com.yu.yurentcar.global;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SiDoType implements EnumValue<String> {
    SEOUL("서울", 11),
    BUSAN("부산", 21),
    DAEGU("대구", 22),
    INCHEON("인천", 23),
    GWANGJU("광주", 24),
    DAEJEON("대전", 25),
    ULSAN("울산", 26),
    SEJONG("세종", 29),
    GYEONGGI("경기도", 31),
    GANGWONDO("강원도", 32),
    CHUNGBUK("충청북도", 33),
    CHUNGNAM("충청남도", 34),
    GYUENGBUK("경상북도", 35),
    GYUENGNAM("경상남도", 36),
    JEONBUK("전라북도", 37),
    JEONNAM("전라남도", 38),
    JEJU("제주도", 39);

    private final String desc;
    //지역코드 : 추후 활용될 가능성을 고려하여 주입
    private final Integer regionCode;

    @Override
    public String  getDbValue() {
        return this.desc;
    }

}
