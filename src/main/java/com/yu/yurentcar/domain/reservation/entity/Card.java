package com.yu.yurentcar.domain.reservation.entity;


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
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @NotNull
    @Column(name = "card_number",length = 20)
    private String cardNumber;

    @NotNull
    @Column(length = 3)
    private String CVC;

    @NotNull
    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @NotNull
    @Column(name = "register_date")
    private LocalDateTime registerDate;
}
