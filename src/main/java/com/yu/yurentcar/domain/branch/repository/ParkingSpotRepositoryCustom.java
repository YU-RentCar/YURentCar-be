package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.dto.ParkingSpotResponseDto;
import com.yu.yurentcar.global.SiDoType;
import org.springframework.data.geo.Point;

import java.util.List;

public interface ParkingSpotRepositoryCustom {
    Long deleteAllParkingSpotByBranchId(Long branchId);
    List<ParkingSpotResponseDto> getAllParkingSpotByBranchId(Long branchId);
    Point findCarLocationByCarNumber(String carNumber, SiDoType siDo, String branchName);
}