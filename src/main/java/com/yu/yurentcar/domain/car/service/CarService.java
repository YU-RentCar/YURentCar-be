package com.yu.yurentcar.domain.car.service;

import com.yu.yurentcar.domain.car.dto.CarDetailsResponseDto;
import com.yu.yurentcar.domain.car.dto.UsableCarResponseDto;
import com.yu.yurentcar.domain.car.dto.UsableCarSearchRequestDto;
import com.yu.yurentcar.domain.car.repository.CarRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<UsableCarResponseDto> getUsableCarList(UsableCarSearchRequestDto requestDto) {
        return carRepository.searchUsableCar(requestDto);
    }

    public CarDetailsResponseDto getCarDetails(String carNumber) {
        return carRepository.findCarDetailsByCarNumber(carNumber);
    }
}
