package com.yu.yurentcar.domain.branch.service;

import com.yu.yurentcar.domain.branch.dto.ParkingSpotRequestDto;
import com.yu.yurentcar.domain.branch.dto.ParkingSpotRequestList;
import com.yu.yurentcar.domain.branch.dto.ParkingSpotResponseDto;
import com.yu.yurentcar.domain.branch.entity.Branch;
import com.yu.yurentcar.domain.branch.entity.ParkingSpot;
import com.yu.yurentcar.domain.branch.entity.ParkingSpotType;
import com.yu.yurentcar.domain.branch.repository.BranchRepository;
import com.yu.yurentcar.domain.branch.repository.ParkingSpotRepository;
import com.yu.yurentcar.domain.car.entity.Car;
import com.yu.yurentcar.domain.car.repository.CarRepository;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import com.yu.yurentcar.global.SiDoType;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ParkingSpotService {
    private final CarRepository carRepository;
    private final BranchRepository branchRepository;
    private final AdminRepository adminRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    @Transactional(readOnly = true)
    public List<ParkingSpotResponseDto> getParkingSpots(String province, String branchName) {
        //지점으로 branchId를 조회
        Branch lookupBranch = branchRepository.findBranchBySiDoAndBranchName(EnumValueConvertUtils.ofDesc(SiDoType.class, province), branchName);
        if (lookupBranch == null) throw new RuntimeException("없는 지점입니다.");
        //주차장 레포지토리로 branchId로 전부 조회 후 반환
        return parkingSpotRepository.getAllParkingSpotByBranchId(lookupBranch.getBranchId());
    }

    @Transactional
    public Boolean postParkingSpots(ParkingSpotRequestList parkingSpotRequestList, String adminUsername) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        Optional<Branch> lookupBranch = branchRepository.findById(lookupAdmin.get().getBranch().getBranchId());
        if (lookupBranch.isEmpty()) throw new RuntimeException("없는 지점입니다.");

        // 현재 지점의 주차장 정보 삭제
        parkingSpotRepository.deleteAllParkingSpotByBranchId(lookupBranch.get().getBranchId());

        List<ParkingSpot> parkingSpots = new ArrayList<>();

        if (parkingSpotRequestList.getParkingSpotRequestList() == null) {
            throw new RuntimeException("주차장 정보가 입력되지 않았습니다.");
        }

        for (ParkingSpotRequestDto parkingSpot : parkingSpotRequestList.getParkingSpotRequestList()) {
            // 주차한 차량이 있는 경우
            if (parkingSpot.getType().equals("주차 불가능")) {
                Optional<Car> lookupCar = carRepository.findByCarNumber(parkingSpot.getCarNumber());
                if (lookupCar.isEmpty()) throw new RuntimeException("없는 차량입니다.");
                // 다른 지점의 차량인 경우
                if (!lookupCar.get().getBranch().equals(lookupBranch.get())) {
                    throw new RuntimeException("다른 지점의 차량입니다. 등록이 불가능합니다.");
                }
                parkingSpots.add(ParkingSpot.builder()
                        .branch(lookupBranch.get())
                        .type(EnumValueConvertUtils.ofDesc(ParkingSpotType.class, parkingSpot.getType()))
                        .x(parkingSpot.getX())
                        .y(parkingSpot.getY())
                        .car(lookupCar.get())
                        .build());
                continue;
            }
            // 주차한 차량이 없는 경우
            parkingSpots.add(ParkingSpot.builder()
                    .branch(lookupBranch.get())
                    .type(EnumValueConvertUtils.ofDesc(ParkingSpotType.class, parkingSpot.getType()))
                    .x(parkingSpot.getX())
                    .y(parkingSpot.getY())
                    .build());
        }
        // 요청받은 정보로 저장
        parkingSpotRepository.saveAll(parkingSpots);
        return true;
    }

    public Point findCarLocationByCarNumber(String carNumber, String siDo, String branchName) {
        return parkingSpotRepository.findCarLocationByCarNumber(carNumber, EnumValueConvertUtils.ofDesc(SiDoType.class, siDo), branchName);
    }
}