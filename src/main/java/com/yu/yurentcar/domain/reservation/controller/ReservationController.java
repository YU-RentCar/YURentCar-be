package com.yu.yurentcar.domain.reservation.controller;

import com.yu.yurentcar.domain.car.dto.CarResponseDto;
import com.yu.yurentcar.domain.car.dto.CarSpecDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationDetailDto;
import com.yu.yurentcar.domain.reservation.service.ReservationService;
import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value="/users/accidents")
    public ResponseEntity<List<String>> getAccidentListByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getAccidentListByUsername(auth.getUsername()));
    }

    @GetMapping(value="/users/repairs")
    public ResponseEntity<List<String>> getRepairListByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getRepairListByUsername(auth.getUsername()));
    }

    @GetMapping(value="/users/prices")
    public ResponseEntity<Integer> getPriceByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getPriceByUsername(auth.getUsername()));
    }

    @GetMapping(value="/users/cars")
    public ResponseEntity<CarResponseDto> getCarByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getCarByUsername(auth.getUsername()));
    }

    @GetMapping(value="/users/car-specs")
    public ResponseEntity<CarSpecDto> getCarSpecByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getCarSpecByUsername(auth.getUsername()));
    }

    @GetMapping(value="/users/branches/points")
    public ResponseEntity<Point> getBranchPointByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getBranchPointByUsername(auth.getUsername()));
    }
    @GetMapping(value="/users/details")
    public ResponseEntity<ReservationDetailDto> getNowReservationDetailByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("username = "+auth.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getNowReservationDetailByUsername(auth.getUsername()));
    }
}