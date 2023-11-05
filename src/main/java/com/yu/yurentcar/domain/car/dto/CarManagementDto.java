package com.yu.yurentcar.domain.car.dto;

import com.yu.yurentcar.domain.car.entity.CarState;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarManagementDto {
    private String carName;
    private String carNumber;
    private String carState;
    private Long carId;

    public CarManagementDto(String carName, String carNumber, CarState carState, Long carId){
        this.carName = carName;
        this.carNumber = carNumber;
        this.carState = carState.getDesc();
        this.carId = carId;
    }
}
