package com.yu.yurentcar.domain.reservation.repository;

import com.yu.yurentcar.domain.reservation.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}