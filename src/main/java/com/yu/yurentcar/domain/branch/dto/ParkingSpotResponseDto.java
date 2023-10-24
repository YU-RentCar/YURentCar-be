package com.yu.yurentcar.domain.branch.dto;

import com.yu.yurentcar.domain.branch.entity.ParkingSpotType;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingSpotResponseDto {
    private String type;
    private double x;
    private double y;

    public ParkingSpotResponseDto(ParkingSpotType parkingSpotType, double x, double y) {
        this.type = parkingSpotType.getDesc();
        this.x = x;
        this.y = y;
    }
}
