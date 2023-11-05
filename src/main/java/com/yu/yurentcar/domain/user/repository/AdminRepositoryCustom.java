package com.yu.yurentcar.domain.user.repository;

public interface AdminRepositoryCustom {
    Boolean isReservationByAdminBranch(Long reservationId, String adminUsername);
}
