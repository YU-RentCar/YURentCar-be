package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.car.dto.KeyManagementDto;

import java.util.List;

public interface KeyRepositoryCustom {
    List<KeyManagementDto> findKeysByAdmin(String adminUsername);
}