package com.yu.yurentcar.domain.reservation.service;

import com.yu.yurentcar.domain.car.dto.CarResponseDto;
import com.yu.yurentcar.domain.car.dto.CarSpecDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationDetailDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationListResponseDto;
import com.yu.yurentcar.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public List<String> getAccidentListByUsername(String username){
        return reservationRepository.getAccidentListByUsername(username);
    }
    @Transactional(readOnly = true)
    public List<String> getRepairListByUsername(String username){
        return reservationRepository.getRepairListByUsername(username);
    }
    @Transactional(readOnly = true)
    public Integer getPriceByUsername(String username){
        return reservationRepository.getPriceByUsername(username);
    }

    @Transactional(readOnly = true)
    public CarResponseDto getCarByUsername(String username){
        return reservationRepository.getCarDtoByUsername(username);
    }

    @Transactional(readOnly = true)
    public CarSpecDto getCarSpecByUsername(String username){
        return reservationRepository.getCarSpecificationDtoByUsername(username);
    }

    @Transactional(readOnly = true)
    public Point getBranchPointByUsername(String username){
        return reservationRepository.getBranchPointByUsername(username);
    }

    @Transactional(readOnly = true)
    public ReservationDetailDto getNowReservationDetailByUsername(String username) {
        return reservationRepository.findNowReservationDetailByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<ReservationListResponseDto> getReservationListByUsername(String username){
        return reservationRepository.getReservationListByUsername(username);
    }
}
