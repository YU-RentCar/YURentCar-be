package com.yu.yurentcar.domain.car.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
public class CarEventDto {
    private String title;
    private LocalDateTime eventDate;
    private String content;
    private Boolean isRepair;
}
