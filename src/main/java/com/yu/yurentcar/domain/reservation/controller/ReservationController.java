package com.yu.yurentcar.domain.reservation.controller;

import com.yu.yurentcar.domain.reservation.dto.ReservationRequestDto;
import com.yu.yurentcar.domain.reservation.service.ReservationService;
import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Long> makeReservation(@RequestBody ReservationRequestDto requestDto, @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("make reservation : " + requestDto);
        return ResponseEntity.ok(reservationService.makeReservation(requestDto, auth.getUsername()));
    }
}