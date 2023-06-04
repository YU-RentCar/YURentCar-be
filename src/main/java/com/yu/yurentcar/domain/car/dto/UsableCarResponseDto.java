package com.yu.yurentcar.domain.car.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UsableCarResponseDto {
    private String carName;
    private String carNumber;
    private Integer totalDistance;
    private Integer price;
    private String imageUri;

    public UsableCarResponseDto(String carName, String carNumber, Integer totalDistance) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.totalDistance = totalDistance;
    }
}
