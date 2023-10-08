package com.yu.yurentcar.domain.car.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarSpecRequestDto {
    private String carName;
    private Integer maxPassenger;
    private String carSize;
    private String oilType;
    private String transmission;
    private boolean isKorean;
    private String carBrand;
    private LocalDateTime releaseDate;
}
