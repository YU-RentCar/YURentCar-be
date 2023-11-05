package com.yu.yurentcar.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class ReservationBranchDto {
    private String nickname;
    private Long reservationId;
    private String carNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ReservationBranchDto(String nickname, Long reservationId, String carNumber, LocalDateTime startDate, LocalDateTime endDate) {
        this.nickname = nickname;
        this.reservationId = reservationId;
        this.carNumber = carNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
