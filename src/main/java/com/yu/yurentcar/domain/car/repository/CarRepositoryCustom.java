package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.car.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CarRepositoryCustom {
    List<UsableCarResponseDto> searchUsableCar(UsableCarSearchRequestDto requestDto);
    CarDetailsResponseDto findCarDetailsByCarNumber(String carNumber);
    CarResponseDto findCarResponseDtoByCarNumber(String carNumber);
    CarSpecDto findCarSpecByCarNumber(String carNumber);
    List<String> findAccidentListByCarNumber(String carNumber);
    List<String> findRepairListByCarNumber(String carNumber);
    Boolean usableByCarNumberAndDate(String carNumber, LocalDateTime startTime, LocalDateTime endTime);
    List<CarResponseDto> findCarsByCarNumbers(String[] carNumber);
}
