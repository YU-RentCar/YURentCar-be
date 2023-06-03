package com.yu.yurentcar.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@ToString
public class PreferFilterDto {
    private final boolean[] carSizes;
    private final int minCount;
    private final boolean[] oilTypes;
    private final boolean[] transmissions;

    @Builder
    public PreferFilterDto( boolean[] carSizes, int minCount, boolean[] oilTypes, boolean[] transmissions) {
        this.carSizes = carSizes;
        this.minCount = minCount;
        this.oilTypes = oilTypes;
        this.transmissions = transmissions;
    }
}
