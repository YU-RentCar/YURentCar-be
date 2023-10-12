package com.yu.yurentcar.domain.reservation.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ReservationDetailDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String branchName;
    private String carName;
    private String carNumber;
    private List<String> drivers;
    @Builder
    public ReservationDetailDto(LocalDateTime startDate, LocalDateTime endDate, String branchName, String carName, String carNumber) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.branchName = branchName;
        this.carName = carName;
        this.carNumber = carNumber;
        this.drivers = null;
    }

    public ReservationDetailDto updateDrivers(List<String> drivers) {
        this.drivers = drivers;
        return this;
    }

    // TODO : 추후 제공
    //private String userName;
    //private LocalDateTime birthday;
    //private String phoneNumber;
    //private EnumSet<DriverLicense> license;
}