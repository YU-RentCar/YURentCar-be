package com.yu.yurentcar.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Log4j2
@Getter
@NoArgsConstructor
@ToString
public class ReservationRequestDto {
    private String carNumber;
    @JsonFormat(pattern = "yyyy. MM. dd. HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy. MM. dd. HH:mm")
    private LocalDateTime endDate;
    private Integer price;
    private Integer usePoint;

    public ReservationRequestDto(String carNumber, LocalDateTime startDate, LocalDateTime endDate, Integer price, Integer usePoint) {
        this.carNumber = carNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.usePoint = usePoint;
    }
}
