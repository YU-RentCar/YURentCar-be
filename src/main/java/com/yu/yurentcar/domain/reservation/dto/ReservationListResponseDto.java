package com.yu.yurentcar.domain.reservation.dto;

import com.yu.yurentcar.domain.reservation.entity.ReviewType;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Log4j2
public class ReservationListResponseDto {
    private Long reservationId;
    private String carName;
    private String carNumber;
    private Integer totalDistance;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String branchName;
    private Integer price;
    private int reviewType;
    private String photoUrl;


    public ReservationListResponseDto(Long reservationId, String carName, String carNumber, Integer totalDistance, LocalDateTime startDate, LocalDateTime endDate, String branchName, Integer price, Boolean isWritten, String photoUrl) {
        this.reservationId = reservationId;
        this.carName = carName;
        this.carNumber = carNumber;
        this.totalDistance = totalDistance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.branchName = branchName;
        this.price = price;
        //LocalDate의 compareTo 함수는 LocalDate에 대해 검사할 시 둘의 일수 차이를 반환함
        long diff = Duration.between(endDate, LocalDateTime.now()).toDays();
        log.info(isWritten);
        log.info(diff);
        if (isWritten)
            this.reviewType = ReviewType.ALREADY.getDbValue();
        else if (diff < 7)
            this.reviewType = ReviewType.POSSIBLE_POINT.getDbValue();
        else if (diff < 30)
            this.reviewType = ReviewType.POSSIBLE_NO_POINT.getDbValue();
        else
            this.reviewType = ReviewType.IMPOSSIBLE.getDbValue();

        this.photoUrl = photoUrl;
    }
}