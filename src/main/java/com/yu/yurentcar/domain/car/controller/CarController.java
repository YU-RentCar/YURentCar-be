package com.yu.yurentcar.domain.car.controller;

import com.yu.yurentcar.domain.car.dto.*;
import com.yu.yurentcar.domain.car.service.CarService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
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
    public ResponseEntity<CarResponseDto> getCarResponseDTO(@RequestParam String carNumber) {
        return ResponseEntity.ok(carService.getCarResponseDTO(carNumber));
    }

    @GetMapping("/car-specs")
    public ResponseEntity<CarSpecDto> getCarSpecByCarNumber(@RequestParam String carNumber) {
        return ResponseEntity.ok(carService.getCarSpecByCarNumber(carNumber));
    }
    // Table 수정으로 이동
//    @GetMapping("/accidents")
//    public ResponseEntity<List<String>> getAccidentListByCarNumber(@RequestParam String carNumber){
//        return ResponseEntity.ok(carService.getAccidentListByCarNumber(carNumber));
//    }
//
//    @GetMapping("/repairs")
//    public ResponseEntity<List<String>> getRepairListByCarNumber(@RequestParam String carNumber){
//        return ResponseEntity.ok(carService.getRepairListByCarNumber(carNumber));
//    }

    @GetMapping("/views")
    public ResponseEntity<List<CarResponseDto>> getCarListByCarNumbers(@RequestParam String[] carNumbers) {
        return ResponseEntity.ok(carService.getCarListByCarNumbers(carNumbers));
    }

    @PostMapping
    public ResponseEntity<Long> postCar(@RequestBody CarRequestDto carRequestDto, @RequestParam String adminUsername) {
        return ResponseEntity.ok(carService.postCar(carRequestDto, adminUsername));
    }

    @PatchMapping
    public ResponseEntity<Boolean> patchCar(@RequestBody @Valid CarRequestDto carRequestDto, @RequestParam String adminUsername, @RequestParam Long carId) {
        return ResponseEntity.ok(carService.patchCar(carRequestDto, adminUsername, carId));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteCar(@RequestParam String adminUsername, @RequestParam String carNumber) {
        return ResponseEntity.ok(carService.deleteCar( adminUsername, carNumber));
    }
}
