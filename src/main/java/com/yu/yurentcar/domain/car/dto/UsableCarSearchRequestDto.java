package com.yu.yurentcar.domain.car.dto;

import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import com.yu.yurentcar.global.SiDoType;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Log4j2
@Getter
@ToString
public class UsableCarSearchRequestDto {
    @DateTimeFormat(pattern = "yyyy. MM. dd. HH:mm")
    private final LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy. MM. dd. HH:mm")
    private final LocalDateTime endDate;

    private final EnumSet<CarSize> carSizes;
    private final int minCount;
    private final EnumSet<OilType> oilTypes;
    private final EnumSet<Transmission> transmissions;
    private final SiDoType siDo;
    private final String branchName;

    @Builder
    public UsableCarSearchRequestDto(LocalDateTime startDate, LocalDateTime endDate, boolean[] carSizes,
                                     int minCount, boolean[] oilTypes, boolean[] transmissions,
                                     String siDo, String branchName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.carSizes = EnumValueConvertUtils.ofBoolListCode(CarSize.class, carSizes);
        this.minCount = minCount;
        this.oilTypes = EnumValueConvertUtils.ofBoolListCode(OilType.class, oilTypes);
        this.transmissions = EnumValueConvertUtils.ofBoolListCode(Transmission.class, transmissions);
        SiDoType type;
        try {
            type = EnumValueConvertUtils.ofDesc(SiDoType.class, siDo);
        } catch (RuntimeException e) {
            log.error(e);
            type = null;
        }
        this.siDo = type;
        this.branchName = branchName;
    }
}
