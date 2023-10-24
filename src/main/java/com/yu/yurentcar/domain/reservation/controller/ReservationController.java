package com.yu.yurentcar.domain.reservation.controller;

import com.yu.yurentcar.domain.reservation.dto.ReservationRequestDto;
import com.yu.yurentcar.domain.reservation.service.ReservationService;
import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

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

//    @PatchMapping
//    public ResponseEntity<Boolean> patchReservation(@RequestBody ReservationRequestDto requestDto, @RequestParam String adminUsername) {
//        log.info("patchReservation : " + requestDto);
//        return ResponseEntity.status(HttpStatus.OK).body(reservationService.patchReservation(requestDto, adminUsername));
//    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteReservation(@RequestParam Long reservationId, @RequestParam(required = false) String adminUsername, @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("deleteReservation = " + reservationId);
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.deleteReservation(reservationId, adminUsername, auth.getUsername()));
    }
}