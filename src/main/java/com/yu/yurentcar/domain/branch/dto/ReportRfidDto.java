package com.yu.yurentcar.domain.branch.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRfidDto {
    private Long parkingSpotId;
    private Boolean isRfidTagged;
    private String rfid;
}