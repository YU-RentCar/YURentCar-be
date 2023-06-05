package com.yu.yurentcar.domain.reservation.entity;


import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
@ToString
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    @ToString.Exclude
    private Reservation reservation;

    @NotNull
    @Column(length = 100)
    private String title;

    @NotNull
    @Column
    private String description;

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Builder
    public Review(@NotNull Reservation reservation, @NotNull String title, @NotNull String description) {
        this.reservation = reservation;
        this.title = title;
        this.description = description;
    }
}

