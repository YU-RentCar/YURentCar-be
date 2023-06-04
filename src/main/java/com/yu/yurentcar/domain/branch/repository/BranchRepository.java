package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long>, BranchRepositoryCustom {
}