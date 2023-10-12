package com.yu.yurentcar.domain.car.controller;

import com.yu.yurentcar.domain.car.dto.CarEventResponseDto;
import com.yu.yurentcar.domain.car.service.CarEventService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/branches/cars")
public class CarEventController {
    public final CarEventService carEventService;

    public CarEventController(CarEventService carEventService) {
        this.carEventService = carEventService;
    }

    @GetMapping("/accidents")
    public ResponseEntity<List<CarEventResponseDto>> getAccidentListByCarNumber(@RequestParam String carNumber){
        return ResponseEntity.ok(carEventService.getAccidentListByCarNumber(carNumber));
    }

    @GetMapping("/repairs")
    public ResponseEntity<List<CarEventResponseDto>> getRepairListByCarNumber(@RequestParam String carNumber){
        return ResponseEntity.ok(carEventService.getRepairListByCarNumber(carNumber));
    }
}
