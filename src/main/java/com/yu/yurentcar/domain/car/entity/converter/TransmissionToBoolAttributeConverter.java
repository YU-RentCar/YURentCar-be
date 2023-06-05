package com.yu.yurentcar.domain.car.entity.converter;

import com.yu.yurentcar.domain.user.entity.Transmission;
import com.yu.yurentcar.global.utils.enums.converter.AbstractEnumAttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TransmissionToBoolAttributeConverter extends AbstractEnumAttributeConverter<Transmission, Boolean> {
    public static final String ENUM_NAME = "트랜스미션";

    public TransmissionToBoolAttributeConverter() {
        super(Transmission.class, false, ENUM_NAME);
    }

}
