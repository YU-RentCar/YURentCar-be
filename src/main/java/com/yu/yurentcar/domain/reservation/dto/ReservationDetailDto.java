package com.yu.yurentcar.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class ReservationDetailDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String branchName;
    private String carName;
    private String carNumber;

    // TODO : 추후 제공
    //private String userName;
    //private LocalDateTime birthday;
    //private String phoneNumber;
    //private EnumSet<DriverLicense> license;
}