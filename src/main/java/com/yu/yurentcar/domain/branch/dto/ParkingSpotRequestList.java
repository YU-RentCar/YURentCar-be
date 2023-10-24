package com.yu.yurentcar.domain.branch.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingSpotRequestList {
    private List<ParkingSpotRequestDto> parkingSpotRequestList;
}
