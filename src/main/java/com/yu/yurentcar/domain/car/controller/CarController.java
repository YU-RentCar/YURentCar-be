package com.yu.yurentcar.domain.car.controller;

import com.yu.yurentcar.domain.car.dto.CarDetailsResponseDto;
import com.yu.yurentcar.domain.car.dto.UsableCarResponseDto;
import com.yu.yurentcar.domain.car.dto.UsableCarSearchRequestDto;
import com.yu.yurentcar.domain.car.service.CarService;
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
public class CarController {
    public final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<UsableCarResponseDto>> getUsableBranchCarList(UsableCarSearchRequestDto requestDto) {
        log.info("===CarController===");
        log.info("getUsableBranchCarList request : " + requestDto);
        return ResponseEntity.ok().body(carService.getUsableCarList(requestDto));
    }

    @GetMapping("details")
    public ResponseEntity<CarDetailsResponseDto> getCarDetails(@RequestParam String carNumber) {
        return ResponseEntity.ok(carService.getCarDetails(carNumber));
    }
}
