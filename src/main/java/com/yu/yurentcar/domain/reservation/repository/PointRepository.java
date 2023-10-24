package com.yu.yurentcar.domain.reservation.repository;

import com.yu.yurentcar.domain.reservation.entity.Pay;
import com.yu.yurentcar.domain.reservation.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository  extends JpaRepository<Point, Long>, PointRepositoryCustom{
    Optional<Point> findByPayId(Pay pay);
}
