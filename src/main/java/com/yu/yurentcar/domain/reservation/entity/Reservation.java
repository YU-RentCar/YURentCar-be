package com.yu.yurentcar.domain.reservation.entity;


import com.yu.yurentcar.domain.car.entity.Car;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Table(name = "reservation")
@ToString
public class Reservation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @ToString.Exclude
    private Car car;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @NotNull
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @NotNull
    @Column(name = "reservation_price")
    private Integer reservationPrice;

    @Builder
    public Reservation(Car car, User user, LocalDateTime startDate, LocalDateTime endDate, Integer reservationPrice) {
        this.car = car;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationPrice = reservationPrice;
    }

    public Reservation updateReservation(Car car, User user, LocalDateTime startDate, LocalDateTime endDate, Integer reservationPrice) {
        if (car != null)
            this.car = car;
        if (user != null)
            this.user = user;
        if (startDate != null)
            this.startDate = startDate;
        if (endDate != null)
            this.endDate = endDate;
        if (reservationPrice != null)
            this.reservationPrice = reservationPrice;

        return this;
    }

}