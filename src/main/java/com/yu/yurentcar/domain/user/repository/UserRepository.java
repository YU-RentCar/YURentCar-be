package com.yu.yurentcar.domain.user.repository;

import com.yu.yurentcar.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByNickname(String nickname);
}
