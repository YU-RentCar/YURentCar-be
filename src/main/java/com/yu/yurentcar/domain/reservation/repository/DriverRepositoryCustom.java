package com.yu.yurentcar.domain.reservation.repository;

public interface DriverRepositoryCustom {
    Long deleteAllDriversByReservationId(Long reservationId);
}
