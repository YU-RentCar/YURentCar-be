package com.yu.yurentcar.domain.car.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UsableCarResponseDto {
    private String carName;
    private String carNumber;
    private Integer totalDistance;
    private Integer beforePrice;
    private Integer afterPrice;
    private Integer discountRatio;
    private String photoUrl;

    public UsableCarResponseDto(String carName, String carNumber, Integer totalDistance, Integer beforePrice, Integer discountRatio, String photoUrl) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.totalDistance = totalDistance;
        this.beforePrice = beforePrice;
        this.afterPrice = (int) Math.ceil((beforePrice - (beforePrice * discountRatio * 0.01)));
        this.discountRatio = discountRatio;
        this.photoUrl = photoUrl;
    }
}
