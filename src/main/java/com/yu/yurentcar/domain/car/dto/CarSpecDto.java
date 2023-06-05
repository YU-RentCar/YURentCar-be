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
    private String oilType;
    private LocalDateTime releaseDate;
    private LocalDateTime createdAt;
    private Integer maxPassenger;
    private String transmission;
    private String carBrand;
    private String isKorean;

    public CarSpecDto(OilType oilType, LocalDateTime releaseDate, LocalDateTime createdAt, Integer maxPassenger, Transmission transmission, CarBrand carBrand, Boolean isKorean) {
        this.oilType = oilType.getDesc();
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
        this.maxPassenger = maxPassenger;
        this.transmission = transmission.getDesc();
        this.carBrand = carBrand.getDesc();
        this.isKorean = isKorean ? "국산" : "외제";
    }
}
