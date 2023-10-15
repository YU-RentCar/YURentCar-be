package com.yu.yurentcar.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequestDto {
    private String carNumber;
    @JsonFormat(pattern = "yyyy. MM. dd. HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy. MM. dd. HH:mm")
    private LocalDateTime endDate;
    private Integer price;
    private Integer usePoint;
    private String reason;
    private List<DriverDto> drivers;
}
