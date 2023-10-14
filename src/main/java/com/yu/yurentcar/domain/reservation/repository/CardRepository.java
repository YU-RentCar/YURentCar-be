package com.yu.yurentcar.domain.reservation.repository;

import com.yu.yurentcar.domain.reservation.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}