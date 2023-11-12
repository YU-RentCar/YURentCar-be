package com.yu.yurentcar.domain.car.dto;

import com.yu.yurentcar.domain.car.entity.KeyState;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyManagementDto {
    private String carNumber;
    private String rfid;
    private String keyState;
    private Long keyId;
    private Long kioskId;
    private Long slotNumber;

    public KeyManagementDto(String carNumber, String rfid, KeyState keyState, Long keyId, Long kioskId, Long slotNumber) {
        this.carNumber = carNumber;
        this.rfid = rfid;
        this.keyState = keyState.getDesc();
        this.keyId = keyId;
        this.kioskId = kioskId;
        this.slotNumber = slotNumber;
    }
}
