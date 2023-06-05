package com.yu.yurentcar.domain.branch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class NoticeContentResponseDto {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String photoUrl;
    private String description;
}