package com.yu.yurentcar.domain.reservation.controller;

import com.yu.yurentcar.domain.reservation.dto.ReservationBranchDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationDatesDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationPatchRequestDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationRequestDto;
import com.yu.yurentcar.domain.reservation.service.ReservationService;
import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.service.AdminService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final AdminService adminService;

    public ReservationController(ReservationService reservationService, AdminService adminService) {
        this.reservationService = reservationService;
        this.adminService = adminService;
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

    @GetMapping("branch")
    public ResponseEntity<List<ReservationBranchDto>> getReservationListByBranch(@RequestParam String adminUsername, @RequestParam(required = false) Boolean isDone) {
        log.info("get reservations by branchId / admin: " + adminUsername + " isDone: " + isDone);

        Admin admin = adminService.getAdminByUsername(adminUsername);
        List<ReservationBranchDto> reservationDtolist = reservationService.getReservationListByBranch(admin.getBranch().getBranchId(), isDone);

        return ResponseEntity.ok(reservationDtolist);
    }

    @GetMapping("branch/nickname/{nickname}")
    public ResponseEntity<ReservationBranchDto> getReservationListByBranchAndNickname(@PathVariable String nickname, @RequestParam String adminUsername, @RequestParam(required = false) Boolean isDone) {
        log.info("get reservation by branchId and nickname / admin: " + adminUsername + " nickname: " + nickname + " isDone: " + isDone);

        Admin admin = adminService.getAdminByUsername(adminUsername);
        ReservationBranchDto reservationDto = reservationService.getReservationListByBranchAndNickname(admin.getBranch().getBranchId(), nickname, isDone);

        return ResponseEntity.ok(reservationDto);
    }

    @GetMapping("{reservationId}/dates")
    public ResponseEntity<ReservationDatesDto> getReservationStartEndTimes(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.getReservationStartEndTimes(reservationId));
    }
}