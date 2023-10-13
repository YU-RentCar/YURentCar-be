package com.yu.yurentcar.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ReviewResponseDto {
    private String title;
    private String description;

    @Builder
    public ReviewResponseDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
