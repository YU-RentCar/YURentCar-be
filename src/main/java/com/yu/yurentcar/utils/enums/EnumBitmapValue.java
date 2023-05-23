package com.yu.yurentcar.utils.enums;

import java.util.EnumSet;

public interface EnumBitmapValue<E extends Enum<E> & EnumValue<T>, T> {
    EnumSet<E> getEnumSet();

}
