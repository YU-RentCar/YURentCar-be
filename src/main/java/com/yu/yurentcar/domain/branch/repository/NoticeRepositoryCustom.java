package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.dto.NoticeListResponseDto;
import com.yu.yurentcar.global.SiDoType;

import java.util.List;

public interface NoticeRepositoryCustom {
    List<NoticeListResponseDto> getNoticesByBranchName(SiDoType siDo, String branchName, Integer count);
}