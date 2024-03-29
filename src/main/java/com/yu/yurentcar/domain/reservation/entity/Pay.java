package com.yu.yurentcar.domain.reservation.entity;

import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "pay")
@ToString
public class Pay extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long payId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    @ToString.Exclude //Lazy join 이라서 해당 필드 제외하고 출력하도록 설정
    private Card card;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    @ToString.Exclude
    private Reservation reservation;

    @NotNull
    @Column(name = "pay_price")
    private Integer payPrice;

    @Builder
    public Pay(@NotNull Card card, @NotNull Reservation reservation, @NotNull Integer payPrice) {
        this.card = card;
        this.reservation = reservation;
        this.payPrice = payPrice;
    }
}
