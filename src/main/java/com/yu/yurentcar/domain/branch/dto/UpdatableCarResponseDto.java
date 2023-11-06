package com.yu.yurentcar.domain.branch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdatableCarResponseDto {
    private String carName;
    private String carNumber;
}
