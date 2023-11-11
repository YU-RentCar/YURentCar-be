package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.dto.BranchNameResponseDto;

public interface KioskRepositoryCustom {
    BranchNameResponseDto findBranchNameByKioskId(Long kioskId);
}