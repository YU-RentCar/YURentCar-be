package com.yu.yurentcar.domain.branch.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportParkingSpotDto {
    private Long parkingSpotId;
    private Boolean isParking;
}
