package com.yu.yurentcar.domain.reservation.controller;

import com.yu.yurentcar.domain.reservation.dto.PointDetailsResponseDto;
import com.yu.yurentcar.domain.reservation.dto.PointRequestDto;
import com.yu.yurentcar.domain.reservation.service.PointService;
import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/v1/points")
public class PointController {
    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    public ResponseEntity<List<PointDetailsResponseDto>> getPointList(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth,
                                                                      @RequestParam(required = false) String adminUsername) {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.getPointList(auth.getUsername(),adminUsername));
    }

    @GetMapping("/users")
    public ResponseEntity<Integer> getPoint(@RequestParam String adminUsername, @RequestParam String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.getPoint(adminUsername, nickname));
    }

    @PostMapping("/users")
    public ResponseEntity<Long> postPoint(@RequestParam String adminUsername, @RequestBody PointRequestDto pointRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.postPoint(adminUsername, pointRequestDto));
    }

}
