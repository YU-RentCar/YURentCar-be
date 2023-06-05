package com.yu.yurentcar.domain.reservation.service;

import com.yu.yurentcar.domain.car.dto.CarResponseDto;
import com.yu.yurentcar.domain.car.dto.CarSpecDto;
import com.yu.yurentcar.domain.car.repository.CarRepository;
import com.yu.yurentcar.domain.reservation.dto.ReservationDetailDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationListResponseDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationRequestDto;
import com.yu.yurentcar.domain.reservation.entity.Reservation;
import com.yu.yurentcar.domain.reservation.repository.ReservationRepository;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import com.yu.yurentcar.global.utils.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final MailUtils mailUtils;
    private final PointService pointService;

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

    @Transactional
    public Long makeReservation(ReservationRequestDto requestDto, String username) {
        if(!carRepository.usableByCarNumberAndDate(requestDto.getCarNumber(), requestDto.getStartDate(), requestDto.getEndDate()))
            throw new RuntimeException("이미 예약이 있습니다.");

        Reservation reservation = reservationRepository.save(
                Reservation.builder()
                        .reservationPrice(requestDto.getPrice())
                        .startDate(requestDto.getStartDate())
                        .endDate(requestDto.getEndDate())
                        .user(userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("없는 사용자입니다.")))
                        .car(carRepository.findByCarNumber(requestDto.getCarNumber()).orElseThrow(() -> new RuntimeException("없는 차량입니다.")))
                        .build()
        );

        // TODO : 포인트 사용 및 적립은 나중에 추가할 것(현재 결제 로직이 없어서 불가능함)
//        pointService.updatePoint(PointDto.builder()
//                        .price(requestDto.getUsePoint())
//                        .type(PointType.USE)
//                        .payId()
//                .build());

        String message = mailUtils.makeMessageFromReservation(reservation);
        mailUtils.sendMail(username, "YU렌트카 예약 완료",message);
        return reservation.getReservationId();
    }
}
