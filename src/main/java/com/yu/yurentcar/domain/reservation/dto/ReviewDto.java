package com.yu.yurentcar.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReviewDto {
    private Long reservationId;
    private String title;
    private String description;

    @Builder
    public ReviewDto(Long reservationId, String title, String description) {
        this.reservationId = reservationId;
        this.title = title;
        this.description = description;
    }
}