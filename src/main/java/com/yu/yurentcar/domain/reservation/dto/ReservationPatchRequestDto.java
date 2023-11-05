package com.yu.yurentcar.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationPatchRequestDto {
    @JsonFormat(pattern = "yyyy. MM. dd. HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy. MM. dd. HH:mm")
    private LocalDateTime endDate;
    private String carNumber;
}
