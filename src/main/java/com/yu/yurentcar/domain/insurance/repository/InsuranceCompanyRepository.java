package com.yu.yurentcar.domain.insurance.repository;

import com.yu.yurentcar.domain.insurance.entity.InsuranceCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany, Long> {
}