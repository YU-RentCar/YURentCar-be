package com.yu.yurentcar.domain.branch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class NoticeContentResponseDto {
    private String photoUrl;
    private String description;
}