package com.yu.yurentcar.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDatesDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
