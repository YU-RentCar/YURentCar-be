package com.yu.yurentcar.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class AdminLoginResponseDto {
    private String branchName;
    private String province;
}
