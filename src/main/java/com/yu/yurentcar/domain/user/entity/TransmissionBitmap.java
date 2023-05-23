package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.utils.enums.EnumBitmapValue;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumSet;

@Getter
@Setter
public class TransmissionBitmap implements EnumBitmapValue<Transmission, Boolean> {

    private final EnumSet<Transmission> enumSet;

    public TransmissionBitmap(EnumSet<Transmission> enumSet) {
        this.enumSet = enumSet;
    }

    @Override
    public EnumSet<Transmission> getEnumSet() {
        return enumSet;
    }

    @Override
    public String toString() {
        return enumSet.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransmissionBitmap that = (TransmissionBitmap) o;

        //EnumSet 의 equals 을 통해 비교 진행하도록 커스텀
        return enumSet.equals(that.enumSet);
    }

    @Override
    public int hashCode() {
        return enumSet != null ? enumSet.hashCode() : 0;
    }
}
