package com.yu.yurentcar.domain.branch.controller;

import com.yu.yurentcar.domain.branch.dto.ParkingSpotRequestList;
import com.yu.yurentcar.domain.branch.dto.ParkingSpotResponseDto;
import com.yu.yurentcar.domain.branch.service.ParkingSpotService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/v1/branches/parkingSpots")
public class ParkingSpotController {
    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotResponseDto>> getParkingSpot(@RequestParam String province, @RequestParam String branchName) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.getParkingSpots(province, branchName));
    }

    @PostMapping
    public ResponseEntity<Boolean> postParkingSpot(@RequestBody ParkingSpotRequestList parkingSpotRequestList, @RequestParam String adminUsername) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.postParkingSpots(parkingSpotRequestList, adminUsername));
    }

    @GetMapping("location")
    public ResponseEntity<Point> findCarLocationByCarNumber(@RequestParam String carNumber, @RequestParam String province, @RequestParam String branchName) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findCarLocationByCarNumber(carNumber, province, branchName));
    }

}

