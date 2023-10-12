package com.yu.yurentcar.domain.car.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarEventResponseDto {
    private String title;
    private LocalDateTime eventDate;
    private String content;
}
