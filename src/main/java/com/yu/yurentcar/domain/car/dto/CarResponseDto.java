package com.yu.yurentcar.domain.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class CarResponseDto {
    private String carNumber;
    private Integer totalDistance;
    private LocalDateTime createdAt;
}
