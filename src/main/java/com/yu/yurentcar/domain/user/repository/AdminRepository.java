package com.yu.yurentcar.domain.user.repository;

import com.yu.yurentcar.domain.user.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {
    Optional<Admin> findByUsername(String username);
    boolean existsByUsername(String username);
}