package com.yu.yurentcar.domain.reservation.service;

import com.yu.yurentcar.domain.reservation.dto.PointDetailsResponseDto;
import com.yu.yurentcar.domain.reservation.dto.PointDto;
import com.yu.yurentcar.domain.reservation.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PointService {
    private final PointRepository pointRepository;

    @Transactional(readOnly = true)
    public List<PointDetailsResponseDto> getPointList(String username) {
        return pointRepository.findAllPointByUsername(username);
    }

    @Transactional
    public void updatePoint(PointDto pointDto) {
        switch (pointDto.getType()) {
            case REVIEW -> {
                if(pointDto.getReviewId() == null) throw new RuntimeException();
            }
            case ETC -> {
                if(pointDto.getAdminId() == null) throw new RuntimeException();
            }
            case USE -> {
                if(pointDto.getPayId() == null) throw new RuntimeException();
            }
            default -> throw new RuntimeException("적립 대상이 아닙니다");
        }
        pointRepository.save(pointDto.toEntity());
    }
}
