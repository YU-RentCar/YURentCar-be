package com.yu.yurentcar.global.utils.enums.converter;

import com.yu.yurentcar.global.utils.enums.EnumValue;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import jakarta.persistence.AttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class AbstractEnumAttributeConverter<E extends Enum<E> & EnumValue<T>, T>
implements AttributeConverter<E, T> {
    private Class<E> targetEnumClass;
    private boolean nullable;
    private String enumName;

    @Override
    public T convertToDatabaseColumn(E attribute) {
        if(!nullable && attribute == null)
            throw new IllegalArgumentException(String.format("%s(은)는 NULL로 저장할 수 없습니다.", enumName));

        return EnumValueConvertUtils.toCode(attribute);
    }

    @Override
    public E convertToEntityAttribute(T dbData) {
        if(!nullable && dbData == null)
            throw new IllegalArgumentException(String.format("%s(이)가 DB에 NULL 혹은 Empty로(%s) 저장되어 있습니다.", enumName, dbData.toString()));

        return EnumValueConvertUtils.ofCode(targetEnumClass, dbData);
    }
}
