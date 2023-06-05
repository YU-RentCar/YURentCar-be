package com.yu.yurentcar.domain.user.entity.converter;

import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.OilTypeBitmap;
import com.yu.yurentcar.global.utils.enums.EnumBitmapValue;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import com.yu.yurentcar.global.utils.enums.converter.AbstractEnumSetToBitmapAttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;

@Getter
@Converter
public class OilTypeBitmapConverter extends AbstractEnumSetToBitmapAttributeConverter<OilType, Integer> {
    public static final String ENUM_NAME = "유종";

    public OilTypeBitmapConverter() {
        super(OilType.class, ENUM_NAME);
    }

    @Override
    public Integer convertToDatabaseColumn(EnumBitmapValue<OilType, Integer> attribute) {
        return super.convertToDatabaseColumn(attribute);
    }

    @Override
    public EnumBitmapValue<OilType, Integer> convertToEntityAttribute(Integer dbData) {
        return new OilTypeBitmap(EnumValueConvertUtils.ofBitCode(OilType.class, dbData));
    }
}