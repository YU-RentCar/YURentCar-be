package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.utils.enums.EnumBitmapValue;
import lombok.Getter;

import java.util.EnumSet;

@Getter
public class OilTypeBitmap implements EnumBitmapValue<OilType, Integer> {

    private final EnumSet<OilType> enumSet;

    public OilTypeBitmap(EnumSet<OilType> enumSet) {
        this.enumSet = enumSet;
    }

    @Override
    public String toString() {
        return enumSet.toString();
    }

    @Override
    public EnumSet<OilType> getEnumSet() {
        return enumSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OilTypeBitmap that = (OilTypeBitmap) o;

        //EnumSet 의 equals 을 통해 비교 진행하도록 커스텀
        return enumSet.equals(that.enumSet);
    }

    @Override
    public int hashCode() {
        return enumSet != null ? enumSet.hashCode() : 0;
    }
}
