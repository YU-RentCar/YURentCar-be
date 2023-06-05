package com.yu.yurentcar.domain.user.entity.converter;

import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.global.utils.enums.converter.AbstractEnumSetBoolAttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CarSizeBoolArrayConverter extends AbstractEnumSetBoolAttributeConverter<CarSize, Integer> {
    public static final String ENUM_NAME = "차량크기";

    public CarSizeBoolArrayConverter() {
        super(CarSize.class, true, ENUM_NAME);
    }

}
