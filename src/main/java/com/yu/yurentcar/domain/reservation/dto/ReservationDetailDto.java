package com.yu.yurentcar.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String photoUrl;
    private Long reservationId;

    @Builder
    public ReservationDetailDto(LocalDateTime startDate, LocalDateTime endDate, String branchName, String carName, String carNumber, String photoUrl, Long reservationId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.branchName = branchName;
        this.carName = carName;
        this.carNumber = carNumber;
        this.drivers = null;
        this.photoUrl = photoUrl;
        this.reservationId = reservationId;
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