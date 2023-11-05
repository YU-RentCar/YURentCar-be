package com.yu.yurentcar.domain.car.dto;

import com.yu.yurentcar.domain.car.entity.KeyState;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyManagementDto {
    private String carName;
    private String carNumber;
    private String rfid;
    private String keyState;
    private Long keyId;

    public KeyManagementDto(String carName, String carNumber, String rfid, KeyState keyState, Long keyId) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.rfid = rfid;
        this.keyState = keyState.getDesc();
        this.keyId = keyId;
    }
}
