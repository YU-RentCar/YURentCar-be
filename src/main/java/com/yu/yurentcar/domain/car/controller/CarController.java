package com.yu.yurentcar.domain.car.controller;

import com.yu.yurentcar.domain.car.dto.*;
import com.yu.yurentcar.domain.car.service.CarService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<Long> postCar(@RequestPart(value = "carRequest") @Valid CarRequestDto carRequestDto,
                                        @RequestParam String adminUsername,
                                        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(carService.postCar(carRequestDto, adminUsername, file));
    }

    @PatchMapping
    public ResponseEntity<Boolean> patchCar(@RequestPart(value = "carRequest") @Valid CarPatchRequestDto carPatchRequestDto,
                                            @RequestParam String adminUsername,
                                            @RequestParam Long carId,
                                            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(carService.patchCar(carPatchRequestDto, adminUsername, carId, file));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteCar(@RequestParam String adminUsername, @RequestParam String carNumber) {
        return ResponseEntity.ok(carService.deleteCar(adminUsername, carNumber));
    }

    @GetMapping("/management")
    public ResponseEntity<List<CarManagementDto>> getCarListByAdmin(@RequestParam String adminUsername) {
        return ResponseEntity.ok(carService.getCarListByAdmin(adminUsername));
    }

    @PostMapping("/states")
    public ResponseEntity<Boolean> changeCarState(@RequestBody CarStateRequestDto carStateRequestDto, @RequestParam String adminUsername) {
        return ResponseEntity.ok(carService.changeCarState(carStateRequestDto, adminUsername));
    }
}
