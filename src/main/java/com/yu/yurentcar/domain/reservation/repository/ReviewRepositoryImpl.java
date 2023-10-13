package com.yu.yurentcar.domain.reservation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.reservation.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import static com.yu.yurentcar.domain.reservation.entity.QReview.review;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ReviewResponseDto findReviewByReservationId(Long reservationId) {

        return queryFactory.select(Projections.constructor(ReviewResponseDto.class,
                        review.title,
                        review.description))
                .from(review)
                .where(review.reservation.reservationId.eq(reservationId))
                .fetchFirst();
    }
}
