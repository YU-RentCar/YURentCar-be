package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.global.SiDoType;

import java.util.List;

public interface BranchRepositoryCustom {
    List<String> findBranchNameListBySiDo(SiDoType siDo);
}
