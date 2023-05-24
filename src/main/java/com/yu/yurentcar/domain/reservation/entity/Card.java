package com.yu.yurentcar.domain.reservation.entity;


import com.yu.yurentcar.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    private User user;

    @NotNull
    @Column(name = "card_number", length = 20, updatable = false)
    private String cardNumber;

    @NotNull
    @Length(min = 3, max = 3)
    @Column(length = 3, updatable = false)
    private String CVC;

    @NotNull
    @Future // 미래 시간
    @Column(name = "expired_date", updatable = false)
    private LocalDateTime expiredDate;

    @NotNull
    @PastOrPresent // 현재 또는 현재 이전
    @Column(name = "register_date")
    private LocalDateTime registerDate;
}
