package com.yu.yurentcar.domain.branch.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticePatchDto {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private String description;
    private Boolean isModified;

    public static NoticeDto toNoticeDto(NoticePatchDto noticePatchDto) {
        return NoticeDto.builder()
                .title(noticePatchDto.getTitle())
                .startDate(noticePatchDto.getStartDate())
                .finishDate(noticePatchDto.getFinishDate())
                .description(noticePatchDto.getDescription())
                .build();
    }
}