package com.yu.yurentcar.domain.car.controller;

import com.yu.yurentcar.domain.car.dto.*;
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

    @GetMapping("/simple")
    public ResponseEntity<CarResponseDto> getCarResponseDTO(@RequestParam String carNumber){
        return ResponseEntity.ok(carService.getCarResponseDTO(carNumber));
    }

    @GetMapping("/car-specs")
    public ResponseEntity<CarSpecDto> getCarSpecByCarNumber(@RequestParam String carNumber){
        return ResponseEntity.ok(carService.getCarSpecByCarNumber(carNumber));
    }

    @GetMapping("/accidents")
    public ResponseEntity<List<String>> getAccidentListByCarNumber(@RequestParam String carNumber){
        return ResponseEntity.ok(carService.getAccidentListByCarNumber(carNumber));
    }

    @GetMapping("/repairs")
    public ResponseEntity<List<String>> getRepairListByCarNumber(@RequestParam String carNumber){
        return ResponseEntity.ok(carService.getRepairListByCarNumber(carNumber));
    }

    @GetMapping("/views")
    public ResponseEntity<List<CarResponseDto>> getCarListByCarNumbers(@RequestParam String[] carNumbers){
        return ResponseEntity.ok(carService.getCarListByCarNumbers(carNumbers));
    }
}
