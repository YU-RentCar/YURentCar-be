package com.yu.yurentcar.domain.car.controller;

import com.yu.yurentcar.domain.car.dto.CarSpecRequestDto;
import com.yu.yurentcar.domain.car.service.CarSpecService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/car-spec")
public class CarSpecController {
    private final CarSpecService carSpecService;

    @GetMapping(value = "/car-sizes")
    public ResponseEntity<List<String>> getCarSizeList() {
        return ResponseEntity.status(HttpStatus.OK).body(carSpecService.getCarSize());
    }

    @GetMapping(value = "/oil-types")
    public ResponseEntity<List<String>> getOilTypeList() {
        return ResponseEntity.status(HttpStatus.OK).body(carSpecService.getOilTypes());
    }

    @GetMapping(value = "/transmissions")
    public ResponseEntity<List<String>> getTransmissionList() {
        return ResponseEntity.status(HttpStatus.OK).body(carSpecService.getTransmissions());
    }

    @PostMapping
    public ResponseEntity<Long> postCarSpec(@RequestBody CarSpecRequestDto carSpecRequestDto, @RequestParam String username) {
        log.info("postCarSpec = " + carSpecRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(carSpecService.postCarSpec(carSpecRequestDto, username));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteCarSpec(@RequestParam Long carSpecId, @RequestParam String username) {
        log.info("deleteCarSpec = " + carSpecId);
        return ResponseEntity.status(HttpStatus.OK).body(carSpecService.deleteCarSpec(carSpecId, username));
    }
}