package com.yu.yurentcar.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ReservationBranchDto {
    private String nickname;
    private Long reservationId;
    private String carNumber;

    @Builder
    public ReservationBranchDto(String nickname, Long reservationId, String carNumber) {
        this.nickname = nickname;
        this.reservationId = reservationId;
        this.carNumber = carNumber;
    }
}
