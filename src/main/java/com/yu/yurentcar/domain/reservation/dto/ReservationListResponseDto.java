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
    //private String photoUrl;
    private String branchName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ReviewType reviewType;

    public ReservationListResponseDto(String branchName, LocalDateTime startDate, LocalDateTime endDate, Boolean isWritten) {
        this.branchName = branchName;
        this.startDate = startDate;
        this.endDate = endDate;
        //LocalDate의 compareTo 함수는 LocalDate에 대해 검사할 시 둘의 일수 차이를 반환함
        long diff = Duration.between(endDate, LocalDateTime.now()).toDays();
        log.info(isWritten);
        log.info(diff);
        if(isWritten)
            this.reviewType = ReviewType.ALREADY;
        else if(diff < 7)
            this.reviewType = ReviewType.POSSIBLE_POINT;
        else if(diff < 30)
            this.reviewType = ReviewType.POSSIBLE_NO_POINT;
        else
            this.reviewType = ReviewType.IMPOSSIBLE;
    }
}