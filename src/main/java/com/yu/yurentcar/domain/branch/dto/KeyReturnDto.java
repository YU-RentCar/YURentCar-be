package com.yu.yurentcar.domain.branch.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyReturnDto {
    private String rfid;
    private Long kioskId;
}