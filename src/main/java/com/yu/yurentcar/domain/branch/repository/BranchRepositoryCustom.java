package com.yu.yurentcar.domain.branch.repository;

import java.util.List;

public interface BranchRepositoryCustom {
    List<String> findBranchNameListBySiDo(String siDo);
}
