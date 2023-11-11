package com.yu.yurentcar.domain.car.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarStateRequestDto {
    private Long carId;
    private String carState;
}
