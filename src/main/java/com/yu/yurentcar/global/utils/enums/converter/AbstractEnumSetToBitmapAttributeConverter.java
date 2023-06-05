package com.yu.yurentcar.global.utils.enums.converter;

import com.yu.yurentcar.global.utils.enums.EnumBitmapValue;
import com.yu.yurentcar.global.utils.enums.EnumValue;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import jakarta.persistence.AttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractEnumSetToBitmapAttributeConverter<E extends Enum<E> & EnumValue<T>, T>
        implements AttributeConverter<EnumBitmapValue<E, T>, Integer> {

    private final Class<E> targetEnumClass;
    private final String enumName;

    @Override
    public Integer convertToDatabaseColumn(EnumBitmapValue<E, T> attribute) {
        if((attribute == null || attribute.getEnumSet() == null
                                            || attribute.getEnumSet().isEmpty()))
            return 0;
        return EnumValueConvertUtils.toBitCode(attribute.getEnumSet());
    }

    @Override
    public abstract EnumBitmapValue<E, T> convertToEntityAttribute(Integer dbData);
}
