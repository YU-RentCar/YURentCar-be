package com.yu.yurentcar.domain.branch.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private String description;
}