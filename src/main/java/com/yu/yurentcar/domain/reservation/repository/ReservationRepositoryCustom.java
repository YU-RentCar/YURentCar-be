package com.yu.yurentcar.domain.reservation.repository;

import com.yu.yurentcar.domain.car.dto.CarResponseDto;
import com.yu.yurentcar.domain.car.dto.CarSpecDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationDetailDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationListResponseDto;
import org.springframework.data.geo.Point;

import java.util.List;

public interface ReservationRepositoryCustom {

    //차량에서 아래 기능 구현하고 service 에서 호출하도록 구현
    List<String> getAccidentListByUsername(String username);

    List<String> getRepairListByUsername(String username);

    Integer getPriceByUsername(String username);

    CarResponseDto getCarDtoByUsername(String username);

    CarSpecDto getCarSpecificationDtoByUsername(String username);

    Point getBranchPointByUsername(String username);

    ReservationDetailDto findNowReservationDetailByUsername(String username);

    List<ReservationListResponseDto> getReservationListByUsername(String username);
}