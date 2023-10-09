package com.yu.yurentcar.domain.reservation.repository;

import com.yu.yurentcar.domain.reservation.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, Long> {
}