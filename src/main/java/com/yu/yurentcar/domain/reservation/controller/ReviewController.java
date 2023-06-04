package com.yu.yurentcar.domain.reservation.controller;

import com.yu.yurentcar.domain.reservation.dto.ReviewDto;
import com.yu.yurentcar.domain.reservation.service.ReviewService;
import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("api/v1/reservations/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Boolean> insertReview(@RequestBody ReviewDto reviewDto, @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("reviewDto = " + reviewDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.insertReview(reviewDto,auth.getUsername()));
    }
}