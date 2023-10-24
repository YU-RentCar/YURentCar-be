package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long>, ParkingSpotRepositoryCustom {

}