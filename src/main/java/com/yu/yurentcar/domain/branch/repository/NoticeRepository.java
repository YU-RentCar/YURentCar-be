package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long>, NoticeRepositoryCustom{
}