package com.yu.yurentcar.domain.car.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyPatchDto {
    private String carNumber;
    private Long kioskId;
    private Long slotNumber;
    private String rfid;
    private String state;
    private Long keyId;
}
