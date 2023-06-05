package com.yu.yurentcar.domain.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class CarResponseDto {
    private String carName;
    private String carNumber;
    private Integer totalDistance;
}
