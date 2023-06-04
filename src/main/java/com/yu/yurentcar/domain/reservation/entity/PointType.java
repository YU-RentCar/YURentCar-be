package com.yu.yurentcar.domain.reservation.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PointType {
    REVIEW("리뷰 작성"), RESERVATION("예약"), USE("사용"), ETC("기타");
    private final String desc;
}

