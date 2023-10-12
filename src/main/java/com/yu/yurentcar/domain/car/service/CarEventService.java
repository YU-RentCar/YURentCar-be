package com.yu.yurentcar.domain.car.service;

import com.yu.yurentcar.domain.car.dto.CarEventResponseDto;
import com.yu.yurentcar.domain.car.repository.CarEventRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class CarEventService {
    private final CarEventRepository carEventRepository;

    public CarEventService( CarEventRepository carEventRepository) {
        this.carEventRepository = carEventRepository;
    }

    @Transactional
    public List<CarEventResponseDto> getRepairListByCarNumber(String carNumber) {
        return carEventRepository.getRepairListByCarNumber(carNumber);
    }

    @Transactional
    public List<CarEventResponseDto> getAccidentListByCarNumber(String carNumber) {
        return carEventRepository.getAccidentListByCarNumber(carNumber);
    }
}