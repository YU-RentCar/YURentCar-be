package com.yu.yurentcar.domain.user.entity.converter;

import com.yu.yurentcar.domain.user.entity.DriverLicense;
import com.yu.yurentcar.global.utils.enums.converter.AbstractEnumSetStringAttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DriverLicenseSetToStringArrayConverter extends AbstractEnumSetStringAttributeConverter<DriverLicense, String> {
    public static final String ENUM_NAME = "면허증";

    public DriverLicenseSetToStringArrayConverter() {
        super(DriverLicense.class, true, ENUM_NAME);
    }
}
