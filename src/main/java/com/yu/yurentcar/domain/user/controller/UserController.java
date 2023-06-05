package com.yu.yurentcar.domain.user.controller;

import com.yu.yurentcar.domain.car.dto.CarResponseDto;
import com.yu.yurentcar.domain.car.dto.CarSpecDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationDetailDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationListResponseDto;
import com.yu.yurentcar.domain.reservation.service.ReservationService;
import com.yu.yurentcar.domain.user.dto.*;
import com.yu.yurentcar.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final ReservationService reservationService;

    // no auth
    @GetMapping(value = "/nicknames/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname) {
        log.info("new nickname : " + nickname);
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkNicknameDuplicate(nickname));
    }

    @GetMapping(value = "/prefer-filter")
    public ResponseEntity<PreferFilterDto> getPreferFilter(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPreferFilter(auth.getNickname()));
    }

    @PatchMapping(value = "/prefer-filter")
    public ResponseEntity<Boolean> changePreferFilter(@RequestBody PreferFilterDto preferFilterDto, @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("data : " + preferFilterDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.changePreferFilter(auth.getUsername(),preferFilterDto)
        );
    }

    @PatchMapping(value = "/nicknames")
    public ResponseEntity<Boolean> changeNickname(@RequestBody ChangeNicknameDto changeNicknameDto,
                                               @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("Username : " + auth.getUsername() + " nickname : " + changeNicknameDto.getNickname());
        return ResponseEntity.status(HttpStatus.OK).body(userService.changeNickname(auth.getUsername(), changeNicknameDto.getNickname()));
    }

    // TODO : 추후 논의 후 추가
//    @PatchMapping(value = "/profiles")
//    public ResponseEntity<User> changeUserProfile(@RequestBody UserProfileDto userProfileDto) {
//        log.info("user profiles change : " + userProfileDto.toString());
//        return ResponseEntity.status(HttpStatus.OK).body(userService.changeProfile(userProfileDto));
//    }

    @GetMapping(value = "/points")
    public ResponseEntity<Integer> getPoint(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("username : " + auth.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPoint(auth.getUsername()));
    }

    @GetMapping(value = "/profiles")
    public ResponseEntity<UserProfileDto> getUserProfile(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        log.info("username : " + auth.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfile(auth.getUsername()));
    }

    @GetMapping(value="/licenses")
    public ResponseEntity<DriverLicenseResponseDto> getLicense(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        log.info("username : " + auth.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(userService.getLicense(auth.getUsername()));
    }

    @GetMapping(value="/reservations/accidents")
    public ResponseEntity<List<String>> getAccidentListByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getAccidentListByUsername(auth.getUsername()));
    }

    @GetMapping(value="/reservations/repairs")
    public ResponseEntity<List<String>> getRepairListByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getRepairListByUsername(auth.getUsername()));
    }

    @GetMapping(value="/reservations/prices")
    public ResponseEntity<Integer> getPriceByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getPriceByUsername(auth.getUsername()));
    }

    @GetMapping(value="/reservations/cars")
    public ResponseEntity<CarResponseDto> getCarByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getCarByUsername(auth.getUsername()));
    }

    @GetMapping(value="/reservations/car-specs")
    public ResponseEntity<CarSpecDto> getCarSpecByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getCarSpecByUsername(auth.getUsername()));
    }

    @GetMapping(value="/reservations/branches/location")
    public ResponseEntity<Point> getBranchPointByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getBranchPointByUsername(auth.getUsername()));
    }
    @GetMapping(value="/reservations/details")
    public ResponseEntity<ReservationDetailDto> getNowReservationDetailByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("username = "+auth.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getNowReservationDetailByUsername(auth.getUsername()));
    }

    @GetMapping(value="/reservations")
    public ResponseEntity<List<ReservationListResponseDto>> getReservationListByUsername(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("username = "+auth.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getReservationListByUsername(auth.getUsername()));
    }
}
