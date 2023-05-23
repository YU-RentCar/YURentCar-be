package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.utils.enums.converter.AbstractEnumSetBoolAttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CarSizeBoolArrayConverter extends AbstractEnumSetBoolAttributeConverter<CarSize, Integer> {
    public static final String ENUM_NAME = "차량음크기";

    public CarSizeBoolArrayConverter() {
        super(CarSize.class, false, ENUM_NAME);
    }

}
