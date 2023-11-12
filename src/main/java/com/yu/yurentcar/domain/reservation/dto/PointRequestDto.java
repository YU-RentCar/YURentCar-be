package com.yu.yurentcar.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PointRequestDto {
    private Integer price;
    private String reason;
    private String type;
    private String nickname;
    private Long reviewId;
    private Long payId;
}