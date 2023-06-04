package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.car.dto.CarDetailsResponseDto;
import com.yu.yurentcar.domain.car.dto.UsableCarResponseDto;
import com.yu.yurentcar.domain.car.dto.UsableCarSearchRequestDto;

import java.util.List;

public interface CarRepositoryCustom {
    List<UsableCarResponseDto> searchUsableCar(UsableCarSearchRequestDto requestDto);
    CarDetailsResponseDto findCarDetailsByCarNumber(String carNumber);
}
