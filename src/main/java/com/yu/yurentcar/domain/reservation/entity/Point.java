package com.yu.yurentcar.domain.reservation.entity;

import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @NotNull
    @Column
    private Integer price;

    @NotNull
    @Column
    private String reason;

    @NotNull
    @Column(length = 10)
    private String type;

    @NotNull
    @Column(name = "accumulation_date")//적립일
    private LocalDateTime accumulationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin adminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_id")
    private Pay payId;
}
