package com.yu.yurentcar.domain.user.dto;

import com.yu.yurentcar.domain.user.entity.*;
import com.yu.yurentcar.domain.user.entity.converter.CarSizeBoolArrayConverter;
import com.yu.yurentcar.domain.user.entity.converter.OilTypeBitmapConverter;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.EnumSet;
import java.util.List;

@Log4j2
@Getter
@ToString
public class SignupRequestDto {
    private final String nickname;
    private final boolean[] carSizes;
    //private final EnumSet<CarSize> carSizeEnumSet;
    private final int minCount;
    private final boolean[] oilTypes;
    //private final OilTypeBitmap oilTypeBitmap;
    private final boolean[] transmissions;
    //private final TransmissionBitmap transmissionBitmap;

    // TODO enumset
    public SignupRequestDto(String nickname, boolean[] carSizes, int minCount, boolean[] oilTypes, boolean[] transmissions) {
        this.nickname = nickname;
        this.carSizes = carSizes;
        this.minCount = minCount;
        this.oilTypes = oilTypes;
        this.transmissions = transmissions;
    }

    //    public SignupRequestDto(String nickname, boolean[] carSizes, int minCount, List<Boolean> oilTypes1, List<Boolean> oilTypes, List<Boolean> transmissions1, List<Boolean> transmissions) {
//        this.nickname = nickname;
//        //this.carSizes = carSizes;
//        this.carSizeEnumSet = toEnumSetCarSizes(carSizes);
//        this.minCount = minCount;
//        this.oilTypes = oilTypes1;
//        this.oilTypeBitmap = toOilTypeBitmap(oilTypes);
//        this.transmissions = transmissions1;
//        this.transmissionBitmap = toTransmissionBitmap(transmissions);
//    }
    public EnumSet<CarSize> toEnumSetCarSizes(boolean[] carSizes){
        CarSizeBoolArrayConverter converter = new CarSizeBoolArrayConverter();
        return converter.convertToEntityAttribute(carSizes);
    }

    public OilTypeBitmap toOilTypeBitmap(List<Boolean> oilTypes){
        EnumSet<OilType> temp =EnumSet.noneOf(OilType.class);

        EnumSet.allOf(OilType.class).stream().filter(v -> oilTypes.get(v.ordinal())).forEach(temp::add);

        return new OilTypeBitmap(temp);
    }

    public TransmissionBitmap toTransmissionBitmap(List<Boolean> transmissions){
        EnumSet<Transmission> temp1 = EnumSet.allOf(Transmission.class);
        int i = 1;
        for(Boolean value : transmissions){
            if(!value){
                switch (i) {
                    case 1 -> {
                        temp1.remove(Transmission.MANUAL);
                    }
                    case 2 -> {
                        temp1.remove(Transmission.AUTOMATIC);
                    }
                }
            }
            i++;
        }
        return new TransmissionBitmap(temp1);
    }
//    public User toEntity(){
//        return User.builder().nickname(nickname).preferSize(carSizes).preferMinPassenger(minCount).preferOilTypeSet(oilTypes).preferTransmissionSet(transmissions).build();
//    }
}
