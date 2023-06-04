package com.yu.yurentcar.domain.reservation.repository;

import com.yu.yurentcar.domain.reservation.dto.PointDetailsResponseDto;

import java.util.List;

public interface PointRepositoryCustom {
    List<PointDetailsResponseDto> findAllPointByUsername(String username);
}