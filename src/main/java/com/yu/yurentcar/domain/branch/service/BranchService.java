package com.yu.yurentcar.domain.branch.service;

import com.yu.yurentcar.domain.branch.dto.UpdatableCarListRequestParamsDto;
import com.yu.yurentcar.domain.branch.dto.UpdatableCarResponseDto;
import com.yu.yurentcar.domain.branch.repository.BranchRepository;
import com.yu.yurentcar.domain.car.dto.CarManagementDto;
import com.yu.yurentcar.domain.car.repository.CarRepository;
import com.yu.yurentcar.domain.car.service.CarService;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import com.yu.yurentcar.global.SiDoType;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BranchService {
    private final CarRepository carRepository;
    private final BranchRepository branchRepository;
    private final AdminRepository adminRepository;
    private final CarService carService;

    public BranchService(CarRepository carRepository, BranchRepository branchRepository, AdminRepository adminRepository, CarService carService) {
        this.carRepository = carRepository;
        this.branchRepository = branchRepository;
        this.adminRepository = adminRepository;
        this.carService = carService;
    }

    @Transactional(readOnly = true)
    public List<String> getBranchNameList(String siDoString) {
        SiDoType siDo;
        try {
            siDo = EnumValueConvertUtils.ofDesc(SiDoType.class, siDoString);
        } catch (RuntimeException e) {
            siDo = null;
        }
        return branchRepository.findBranchNameListBySiDo(siDo);
    }

    @Transactional(readOnly = true)
    public Point getGeoPoint(String province, String branchName) {
        SiDoType siDo = EnumValueConvertUtils.ofDesc(SiDoType.class, province);
        return branchRepository.getGeoPointByBranchName(siDo, branchName);
    }

    @Transactional(readOnly = true)
    public List<UpdatableCarResponseDto> getUpdatableCarListByBranch(UpdatableCarListRequestParamsDto requestDto) {
        boolean isReservationInAdminBranch = adminRepository.isReservationByAdminBranch(requestDto.getReservationId(), requestDto.getAdminUsername());
        if (!isReservationInAdminBranch)
            throw new RuntimeException("해당 관리자가 없거나 해당 관리자 지점의 예약이 아닙니다.");

        List<CarManagementDto> carNumberListInReservationBranch = carService.getCarListByAdmin(requestDto.getAdminUsername());
        return carNumberListInReservationBranch.stream()
                .filter((car) ->
                        carRepository.updatableByCarNumberAndDate(
                                requestDto.getReservationId(),
                                car.getCarNumber(),
                                requestDto.getStartDate(),
                                requestDto.getEndDate()
                        )
                )
                .map((car) -> new UpdatableCarResponseDto(car.getCarName(), car.getCarNumber()))
                .collect(Collectors.toList());
    }
}
