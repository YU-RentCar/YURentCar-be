package com.yu.yurentcar.domain.reservation.entity;

import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "point")
@ToString
public class Point extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @NotNull
    @Column
    private Integer price;

    @Column
    private String reason;

    @NotNull
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private PointType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    @ToString.Exclude
    private Review reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    @ToString.Exclude
    private Admin adminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_id")
    @ToString.Exclude
    private Pay payId;

    @Builder
    public Point(@NotNull Integer price, String reason, PointType type, Review reviewId, Admin adminId, User userId, Pay payId) {
        this.price = price;
        this.reason = reason;
        this.type = Objects.requireNonNullElse(type, PointType.ETC);
        this.reviewId = reviewId;
        this.adminId = adminId;
        this.userId = userId;
        this.payId = payId;
    }
}
