package com.yu.yurentcar.domain.car.controller;

import com.yu.yurentcar.domain.car.service.CarSpecService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/car-spec")
public class CarSpecController {
    private final CarSpecService carSpecService;

    @GetMapping(value = "/car-sizes")
    public ResponseEntity<List<String>> getCarSizeList(){
        return  ResponseEntity.status(HttpStatus.OK).body(carSpecService.getCarSize());
    }

    @GetMapping(value = "/oil-types")
    public ResponseEntity<List<String>> getOilTypeList(){
        return  ResponseEntity.status(HttpStatus.OK).body(carSpecService.getOilTypes());
    }

    @GetMapping(value = "/transmissions")
    public ResponseEntity<List<String>> getTransmissionList(){
        return  ResponseEntity.status(HttpStatus.OK).body(carSpecService.getTransmissions());
    }
}