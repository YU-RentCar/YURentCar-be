package com.yu.yurentcar.domain.reservation.repository;

import com.yu.yurentcar.domain.reservation.dto.ReviewResponseDto;

public interface ReviewRepositoryCustom {
    ReviewResponseDto findReviewByReservationId(Long reservationId);
}
