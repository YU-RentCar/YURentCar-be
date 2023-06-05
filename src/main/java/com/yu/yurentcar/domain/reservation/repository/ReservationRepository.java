package com.yu.yurentcar.domain.reservation.repository;

import com.yu.yurentcar.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {

}