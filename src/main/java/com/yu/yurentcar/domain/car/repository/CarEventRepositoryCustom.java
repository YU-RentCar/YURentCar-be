package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.car.dto.CarEventResponseDto;

import java.util.List;

public interface CarEventRepositoryCustom {
    List<CarEventResponseDto> getRepairListByCarNumber(String carNumber);
    List<CarEventResponseDto> getAccidentListByCarNumber(String carNumber);

    Long deleteRepairListByCarId(Long carId);
    Long deleteAccidentListByCarId(Long carId);
}