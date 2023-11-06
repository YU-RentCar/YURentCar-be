package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.branch.dto.UpdatableCarResponseDto;
import com.yu.yurentcar.domain.car.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CarRepositoryCustom {
    List<UsableCarResponseDto> searchUsableCar(UsableCarSearchRequestDto requestDto);

    CarDetailsResponseDto findCarDetailsByCarNumber(String carNumber);

    CarResponseDto findCarResponseDtoByCarNumber(String carNumber);

    CarSpecDto findCarSpecByCarNumber(String carNumber);

    //Table 수정으로 이동
//    List<String> findAccidentListByCarNumber(String carNumber);
//    List<String> findRepairListByCarNumber(String carNumber);
    Boolean usableByCarNumberAndDate(String carNumber, LocalDateTime startTime, LocalDateTime endTime);

    Boolean updatableByCarNumberAndDate(Long reservationId, String carNumber, LocalDateTime startTime, LocalDateTime endTime);

    List<CarResponseDto> findCarsByCarNumbers(String[] carNumber);

    List<CarManagementDto> findCarsByAdmin(String adminUsername);

    List<UpdatableCarResponseDto> findUpdatableCarListByDateAndReservationId(LocalDateTime startDate, LocalDateTime endDate, Long reservationId);
}
