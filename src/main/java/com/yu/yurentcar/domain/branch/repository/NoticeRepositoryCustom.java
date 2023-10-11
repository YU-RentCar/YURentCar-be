package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.dto.NoticeSummaryDto;
import com.yu.yurentcar.global.SiDoType;

import java.util.List;

public interface NoticeRepositoryCustom {
    List<NoticeSummaryDto> getNoticesByBranchName(SiDoType siDo, String branchName, Integer count);
}