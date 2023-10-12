package com.yu.yurentcar.domain.car.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class CarAccidentEventDto extends CarEventDto {
    private String title;
    private LocalDateTime eventDate;
    private String content;

    @Builder
    CarAccidentEventDto(String title, LocalDateTime eventDate, String content) {
        super(title, eventDate, content, false);
    }
}
