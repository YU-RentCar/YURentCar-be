package com.yu.yurentcar.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class DriverLicenseResponseDto {
    private String driverLicense;
    private String licenseNumber;
    private LocalDateTime issueDate;
    private LocalDateTime expiredDate;
}
