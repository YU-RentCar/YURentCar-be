package com.yu.yurentcar.domain.reservation.controller;

import com.yu.yurentcar.domain.reservation.dto.ReviewDto;
import com.yu.yurentcar.domain.reservation.dto.ReviewResponseDto;
import com.yu.yurentcar.domain.reservation.service.ReviewService;
import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import com.yu.yurentcar.domain.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reservations/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Boolean> insertReview(@RequestBody ReviewDto reviewDto, @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("reviewDto = " + reviewDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.insertReview(reviewDto,auth.getUsername()));
    }

    @GetMapping
    public ResponseEntity<ReviewResponseDto> getReview(@RequestParam long reservationId, @RequestParam(required = false) String adminUsername, @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("reservationId : " + reservationId);
        ReviewResponseDto reviewDto;

        if(adminService.validAdminByUsername(adminUsername))
            reviewDto = reviewService.getReviewByReservationId(reservationId);
        else if(auth != null)
            reviewDto = reviewService.getReviewByReservationIdWithWriter(reservationId, auth.getUsername());
        else
            throw new RuntimeException("로그인이 필요합니다.");

        log.info("reviewDto" + reviewDto);

        return ResponseEntity.status(HttpStatus.OK).body(reviewDto);
    }
}