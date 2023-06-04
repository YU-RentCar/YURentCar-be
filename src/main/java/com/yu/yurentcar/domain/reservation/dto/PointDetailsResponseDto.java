package com.yu.yurentcar.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class PointDetailsResponseDto {
    private Integer price;
    private String reason;//기타이면 reason 아니면 PointType의 desc로 반환
    private LocalDateTime createdTime;
}
