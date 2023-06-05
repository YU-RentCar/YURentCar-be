package com.yu.yurentcar.domain.user.entity.converter;

import com.yu.yurentcar.domain.user.entity.Transmission;
import com.yu.yurentcar.domain.user.entity.TransmissionBitmap;
import com.yu.yurentcar.global.utils.enums.EnumBitmapValue;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import com.yu.yurentcar.global.utils.enums.converter.AbstractEnumSetToBitmapAttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;

@Getter
@Converter
public class TransmissionBitmapConverter extends AbstractEnumSetToBitmapAttributeConverter<Transmission, Boolean> {
    public static final String ENUM_NAME = "트랜스미션";

    public TransmissionBitmapConverter() {
        super(Transmission.class, ENUM_NAME);
    }

    @Override
    public Integer convertToDatabaseColumn(EnumBitmapValue<Transmission, Boolean> attribute) {
        return super.convertToDatabaseColumn(attribute);
    }

    @Override
    public EnumBitmapValue<Transmission, Boolean> convertToEntityAttribute(Integer dbData) {
        return new TransmissionBitmap(EnumValueConvertUtils.ofBitCode(Transmission.class, dbData));
    }
}
