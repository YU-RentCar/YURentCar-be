package com.yu.yurentcar.global.utils.enums;

import java.util.EnumSet;

public interface EnumBitmapValue<E extends Enum<E> & EnumValue<T>, T> {
    EnumSet<E> getEnumSet();

}
