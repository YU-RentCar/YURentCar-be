package com.yu.yurentcar.domain.car.dto;

import com.yu.yurentcar.domain.car.entity.CarBrand;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class CarSpecDto {
    private OilType oilType;
    private LocalDateTime releaseDate;
    private LocalDateTime createdAt;
    private Integer maxPassenger;
    private Transmission transmission;
    private CarBrand carBrand;
    private Boolean isKorean;
}
