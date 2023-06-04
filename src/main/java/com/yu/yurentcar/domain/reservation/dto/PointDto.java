package com.yu.yurentcar.domain.reservation.dto;

import com.yu.yurentcar.domain.reservation.entity.Pay;
import com.yu.yurentcar.domain.reservation.entity.Point;
import com.yu.yurentcar.domain.reservation.entity.PointType;
import com.yu.yurentcar.domain.reservation.entity.Review;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PointDto {
    private Integer price;
    private String reason;
    private PointType type;
    private Review reviewId;
    private Admin adminId;
    private User userId;
    private Pay payId;
    
    public Point toEntity() {
        return Point.builder()
                .price(this.price)
                .reason(this.reason)
                .type(this.type)
                .reviewId(this.reviewId)
                .userId(this.userId)
                .adminId(this.adminId)
                .payId(this.payId)
                .build();
    }
}
