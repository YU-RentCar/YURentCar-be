package com.yu.yurentcar.domain.user.repository;

import com.yu.yurentcar.domain.user.entity.JoinType;
import com.yu.yurentcar.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByNickname(String nickname);
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByUsernameAndJoinType(String username, JoinType type);
}
