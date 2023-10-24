package com.yu.yurentcar.domain.branch.dto;


import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingSpotRequestDto {
    private String type;
    private double x;
    private double y;
    private String carNumber;
}
