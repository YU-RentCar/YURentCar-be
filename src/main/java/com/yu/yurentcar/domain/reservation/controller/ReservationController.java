package com.yu.yurentcar.domain.reservation.controller;

import com.yu.yurentcar.domain.reservation.dto.ReservationBranchDto;
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

    @GetMapping("branch/{branchId}")
    public ResponseEntity<List<ReservationBranchDto>> getReservationListByBranch(@PathVariable Long branchId, @RequestParam(required = false) String adminUsername, @RequestParam(required = false) Boolean isDone) {
        log.info("get reservations by branchId / branchId:" + branchId + " admin: " + adminUsername + " isDone: " + isDone);

        Admin admin = adminService.getAdminByUsername(adminUsername);
        if(!admin.getBranch().getBranchId().equals(branchId))
            throw new RuntimeException("해당 지점에 대한 권한이 없는 관리자입니다. branchId: " + branchId);

        List<ReservationBranchDto> reservationDtolist = reservationService.getReservationListByBranch(branchId, isDone);

        return ResponseEntity.ok(reservationDtolist);
    }

    @GetMapping("{reservationId}/dates")
    public ResponseEntity<ReservationDatesDto> getReservationStartEndTimes(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.getReservationStartEndTimes(reservationId));
    }
}