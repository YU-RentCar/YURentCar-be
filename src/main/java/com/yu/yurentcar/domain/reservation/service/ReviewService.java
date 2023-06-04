package com.yu.yurentcar.domain.reservation.service;

import com.yu.yurentcar.domain.reservation.dto.ReviewDto;
import com.yu.yurentcar.domain.reservation.entity.Point;
import com.yu.yurentcar.domain.reservation.entity.PointType;
import com.yu.yurentcar.domain.reservation.entity.Reservation;
import com.yu.yurentcar.domain.reservation.entity.Review;
import com.yu.yurentcar.domain.reservation.repository.PointRepository;
import com.yu.yurentcar.domain.reservation.repository.ReservationRepository;
import com.yu.yurentcar.domain.reservation.repository.ReviewRepository;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    private final Integer reviewPoint = 500;

    @Transactional
    public Boolean insertReview(ReviewDto reviewDto, String username) {
        //예약Id로 예약 객체 불러옴
        Optional<Reservation> reservation = reservationRepository.findById(reviewDto.getReservationId());
        if (reservation.isEmpty()) throw new RuntimeException("존재하지 않는 예약입니다.");
        if (!reservation.get().getUser().getUsername().equals(username))
            throw new RuntimeException("예약자와 조회자가 다른 사람입니다.");

        //예약 종료일의 30일 이후의 날짜가 현재 날짜보다 작은 경우
        if (reservation.get().getEndDate().plusDays(30).isBefore(LocalDateTime.now()))
            throw new RuntimeException("30일이 지난 예약은 리뷰 작성이 불가능 합니다.");

        //리뷰 테이블에 저장
        Review savedReview = reviewRepository.save(Review.builder()
                .reservation(reservation.get())
                .title(reviewDto.getTitle())
                .description(reviewDto.getDescription())
                .build());
        //방금 저장한 리뷰테이블의 레코드 불러옴
        //포인트 테이블에 저장
        pointRepository.save(Point.builder()
                .price(reviewPoint)
                .type(PointType.REVIEW)
                .reviewId(savedReview)
                .userId(reservation.get().getUser())
                .build());
        //유저 포인트 업데이트
        userRepository.save(reservation.get().getUser().updatePoint(reviewPoint));
        return true;
    }
}