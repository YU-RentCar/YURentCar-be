package com.yu.yurentcar.global.utils.enums.converter;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import jakarta.persistence.AttributeConverter;
import lombok.Getter;

import java.util.EnumSet;

@Getter
public abstract class AbstractEnumSetStringAttributeConverter<E extends Enum<E> & EnumValue<T>, T>
        implements AttributeConverter<EnumSet<E>, T[]> {
    private final Class<E> targetEnumClass;
    private final boolean nullable;
    private final String enumName;

    public AbstractEnumSetStringAttributeConverter(Class<E> targetEnumClass, boolean nullable, String enumName) {
        this.targetEnumClass = targetEnumClass;
        this.nullable = nullable;
        this.enumName = enumName;
    }

    @Override
    public T[] convertToDatabaseColumn(EnumSet<E> attribute) {
        if(!nullable && (attribute == null || attribute.isEmpty()))
            throw new IllegalArgumentException(String.format("%s(은)는 NULL로 저장할 수 없습니다.", enumName));

        return EnumValueConvertUtils.toCode(targetEnumClass, attribute);
    }

    @Override
    public EnumSet<E> convertToEntityAttribute(T[] dbData) {
        if(!nullable && (dbData == null || dbData.length == 0))
            throw new IllegalArgumentException(String.format("%s(이)가 DB에 NULL 혹은 Empty로(%s) 저장되어 있습니다.", enumName, dbData));

        return EnumValueConvertUtils.ofCode(targetEnumClass, dbData);
    }
}
