package com.yu.yurentcar.domain.branch.dto;

import com.yu.yurentcar.domain.branch.entity.ParkingSpotType;
import lombok.*;

import java.util.Objects;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingSpotResponseDto {
    private String type;
    private double x;
    private double y;
    private String carNumber;

    public ParkingSpotResponseDto(ParkingSpotType parkingSpotType, double x, double y, String carNumber) {
        this.type = parkingSpotType.getDesc();
        this.x = x;
        this.y = y;
        this.carNumber = Objects.requireNonNullElse(carNumber, "");
    }
}
