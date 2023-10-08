package com.yu.yurentcar.domain.car.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyDto {
    private String carNumber;
    private Long keyStorageId;
    private String rfid;
    private String state;
}
