package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.global.SiDoType;
import org.springframework.data.geo.Point;

import java.util.List;

public interface BranchRepositoryCustom {
    List<String> findBranchNameListBySiDo(SiDoType siDo);
    Point getGeoPointByBranchName(SiDoType siDo, String branchName);
}
