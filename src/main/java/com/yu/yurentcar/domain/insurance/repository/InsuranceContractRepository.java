package com.yu.yurentcar.domain.insurance.repository;

import com.yu.yurentcar.domain.insurance.entity.InsuranceContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceContractRepository extends JpaRepository<InsuranceContract, Long> {
}