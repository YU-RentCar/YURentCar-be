package com.yu.yurentcar.domain.reservation.service;

import com.yu.yurentcar.domain.car.dto.CarEventResponseDto;
import com.yu.yurentcar.domain.car.dto.CarResponseDto;
import com.yu.yurentcar.domain.car.dto.CarSpecDto;
import com.yu.yurentcar.domain.car.entity.Car;
import com.yu.yurentcar.domain.car.repository.CarRepository;
import com.yu.yurentcar.domain.reservation.dto.*;
import com.yu.yurentcar.domain.reservation.entity.*;
import com.yu.yurentcar.domain.reservation.repository.*;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.entity.DriverLicense;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import com.yu.yurentcar.global.utils.MailUtils;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class ReservationService {
    private final PointRepository pointRepository;
    private final AdminRepository adminRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final MailUtils mailUtils;
    private final PointService pointService;
    private final DriverRepository driverRepository;
    private final PayRepository payRepository;
    private final CardRepository cardRepository;

    @Transactional(readOnly = true)
    public List<CarEventResponseDto> getAccidentListByUsername(String username) {
        return reservationRepository.getAccidentListByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<CarEventResponseDto> getRepairListByUsername(String username) {
        return reservationRepository.getRepairListByUsername(username);
    }

    @Transactional(readOnly = true)
    public Integer getPriceByUsername(String username) {
        return reservationRepository.getPriceByUsername(username);
    }

    @Transactional(readOnly = true)
    public CarResponseDto getCarByUsername(String username) {
        return reservationRepository.getCarDtoByUsername(username);
    }

    @Transactional(readOnly = true)
    public CarSpecDto getCarSpecByUsername(String username) {
        return reservationRepository.getCarSpecificationDtoByUsername(username);
    }

    @Transactional(readOnly = true)
    public Point getBranchPointByUsername(String username) {
        return reservationRepository.getBranchPointByUsername(username);
    }

    @Transactional(readOnly = true)
    public ReservationDetailDto getNowReservationDetailByUsername(String username) {
        ReservationDetailDto reservationDetailDto = reservationRepository.findNowReservationDetailByUsername(username);
        List<String> drivers = reservationRepository.findNowReservationDriversByUsername(username);
        if (!drivers.isEmpty())
            reservationDetailDto.updateDrivers(drivers);
        return reservationDetailDto;
    }

    @Transactional(readOnly = true)
    public List<ReservationListResponseDto> getReservationListByUsername(String username) {
        return reservationRepository.getReservationListByUsername(username);
    }

    @Transactional
    public Long makeReservation(ReservationRequestDto requestDto, String username) {
        if (!carRepository.usableByCarNumberAndDate(requestDto.getCarNumber(), requestDto.getStartDate(), requestDto.getEndDate()))
            throw new RuntimeException("이미 예약이 있습니다.");

        log.info("예약 완료 : " + requestDto);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("없는 사용자입니다."));

        Reservation reservation = reservationRepository.saveAndFlush(
                Reservation.builder()
                        .reservationPrice(requestDto.getPrice())
                        .startDate(requestDto.getStartDate())
                        .endDate(requestDto.getEndDate())
                        .user(user)
                        .car(carRepository.findByCarNumber(requestDto.getCarNumber()).orElseThrow(() -> new RuntimeException("없는 차량입니다.")))
                        .build()
        );

        // 예약에 대한 운전자리스트 등록
        List<Driver> drivers = new ArrayList<>();
        for (DriverDto driverDto : requestDto.getDrivers()) {
            drivers.add(Driver.builder()
                    .reservation(reservation)
                    .driverName(driverDto.getName())
                    .birthdate(driverDto.getBirthdate())
                    .phoneNumber(driverDto.getPhoneNumber())
                    .driverLicense(EnumValueConvertUtils.ofCode(DriverLicense.class, driverDto.getLicenseType()))
                    .driverNumber(driverDto.getLicenseNumber())
                    .issueDate(driverDto.getIssueDate())
                    .expirationDate(driverDto.getExpirationDate())
                    .build());
        }
        driverRepository.saveAll(drivers);

        //임시 결제 내역 추가
        Optional<Card> lookupCard = cardRepository.findById(1L);
        if (lookupCard.isEmpty()) throw new RuntimeException("없는 카드입니다.");
        Pay pay = payRepository.save(Pay.builder()
                .card(lookupCard.get())
                .reservation(reservation)
                .payPrice(requestDto.getPrice())
                .build());
        // TODO : 포인트 사용 및 적립은 나중에 추가할 것(현재 결제 로직이 없어서 불가능함)
        if (requestDto.getUsePoint() != 0) {
            pointService.updatePoint(PointDto.builder()
                    .price(requestDto.getUsePoint())
                    .reason(requestDto.getReason())
                    .type(PointType.USE)
                    .payId(pay)
                    .userId(user)
                    .build());

            // 사용자 포인트 업데이트
            userRepository.save(user.updatePoint(requestDto.getUsePoint()));
        }


        // 예약 완료 이메일 전송
        String message = mailUtils.makeMessageFromReservation(reservation);
        mailUtils.sendMail(username, "YU렌트카 예약 완료", message);
        return reservation.getReservationId();
    }

    @Transactional
    public Boolean patchReservation(Long reservationId, ReservationPatchRequestDto requestDto, String username) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("해당 예약이 존재하지 않습니다."));
        boolean isReservationInAdminBranch = adminRepository.isReservationByAdminBranch(reservationId, username);
        if (!isReservationInAdminBranch)
            throw new RuntimeException("해당 관리자가 없거나 해당 관리자 지점의 예약이 아닙니다.");
        boolean usable = carRepository.updatableByCarNumberAndDate(reservationId, requestDto.getCarNumber(), requestDto.getStartDate(), requestDto.getEndDate());
        if (!usable)
            return false;

        Car updatedCar = carRepository.findByCarNumber(requestDto.getCarNumber()).orElseThrow(() -> new RuntimeException("존재하지 않는 차량입니다."));
        reservationRepository.save(reservation.updateReservation(updatedCar, null, requestDto.getStartDate(), requestDto.getEndDate(), null));

        return true;
    }

    @Transactional
    public Boolean deleteReservation(Long reservationId, String adminUsername, String username) {
        Optional<Admin> lookupAdmin;
        Optional<Reservation> lookupReservation;
        Optional<User> lookupUser;
        if (adminUsername != null) {// 관리자가 예약 취소하는 경우
            lookupAdmin = adminRepository.findByUsername(adminUsername);
            if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
            lookupReservation = reservationRepository.findById(reservationId);
            if (lookupReservation.isEmpty()) throw new RuntimeException("없는 예약입니다");
            if (!lookupReservation.get().getCar().getBranch().equals(lookupAdmin.get().getBranch())) {
                throw new RuntimeException("취소하려는 예약의 지점과 다른 지점 관리자입니다. 권한이 없습니다.");
            }
            lookupUser = Optional.of(lookupReservation.get().getUser());
        } else {//사용자가 예약 취소하는 경우
            lookupUser = userRepository.findByUsername(username);
            if (lookupUser.isEmpty()) throw new RuntimeException("없는 사용자입니다.");
        }


        // 결제 테이블 삭제
        Optional<Pay> lookupPay = payRepository.findByReservationReservationId(reservationId);
        if (lookupPay.isEmpty()) throw new RuntimeException("결제 내역이 없습니다.");
        payRepository.delete(lookupPay.get());
        Optional<com.yu.yurentcar.domain.reservation.entity.Point> lookupPoint = pointRepository.findByPayId(lookupPay.get());
        if (lookupPoint.isEmpty()) {
            log.info("결제할 때 포인트를 사용하지 않았습니다.");
        } else {
            // 포인트 테이블 삭제
            pointRepository.delete(lookupPoint.get());
            // 사용자 보유포인트 갱신
            userRepository.save(lookupUser.get().updatePoint(Math.abs(lookupPoint.get().getPrice())));
        }
        //드라이버들 삭제랑 예약 테이블 삭제하기
        driverRepository.deleteAllDriversByReservationId(reservationId);
        // insurance_contract 테이블 내역도 삭제 <- 추후 보험계약 구현시 추가
        // reservation 테이블 내역 삭제
        reservationRepository.deleteById(reservationId);

        return true;
    }

    @Transactional(readOnly = true)
    public List<ReservationBranchDto> getReservationListByBranch(Long branchId, Boolean isDone) {
        return reservationRepository.getReservationListByBranchId(branchId, isDone);
    }

    @Transactional(readOnly = true)
    public ReservationBranchDto getReservationListByBranchAndNickname(Long branchId, String nickname, Boolean isDone) {
        return reservationRepository.getReservationListByBranchIdAndNickname(branchId, nickname, isDone);
    }

    @Transactional(readOnly = true)
    public ReservationDatesDto getReservationStartEndTimes(Long reservationId) {
        return reservationRepository.getReservationStartDateAndEndDateByReservationId(reservationId);
    }
}
