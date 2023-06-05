package com.yu.yurentcar.global;

import com.yu.yurentcar.global.utils.enums.converter.AbstractEnumAttributeConverter;

public class SiDoTypeToStringAttributeConverter extends AbstractEnumAttributeConverter<SiDoType, String> {
    public static final String ENUM_NAME = "시/도";

    public SiDoTypeToStringAttributeConverter() {
        super(SiDoType.class, false, ENUM_NAME);
    }
}