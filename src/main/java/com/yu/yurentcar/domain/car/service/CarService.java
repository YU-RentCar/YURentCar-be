package com.yu.yurentcar.domain.car.service;

import com.yu.yurentcar.domain.car.dto.*;
import com.yu.yurentcar.domain.car.repository.CarRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional(readOnly = true)
    public List<UsableCarResponseDto> getUsableCarList(UsableCarSearchRequestDto requestDto) {
        return carRepository.searchUsableCar(requestDto);
    }

    @Transactional(readOnly = true)
    public CarDetailsResponseDto getCarDetails(String carNumber) {
        return carRepository.findCarDetailsByCarNumber(carNumber);
    }

    @Transactional(readOnly = true)
    public CarResponseDto getCarResponseDTO(String carNumber){
        return carRepository.findCarResponseDtoByCarNumber(carNumber);
    }

    @Transactional(readOnly = true)
    public CarSpecDto getCarSpecByCarNumber(String carNumber){
        return carRepository.findCarSpecByCarNumber(carNumber);
    }

    @Transactional(readOnly = true)
    public List<String> getAccidentListByCarNumber(String carNumber){
        return carRepository.findAccidentListByCarNumber(carNumber);
    }

    @Transactional(readOnly = true)
    public List<String> getRepairListByCarNumber(String carNumber){
        return carRepository.findRepairListByCarNumber(carNumber);
    }
}
