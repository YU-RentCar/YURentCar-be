package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.dto.NoticeResponseDto;
import com.yu.yurentcar.global.SiDoType;

import java.util.List;

public interface NoticeRepositoryCustom {
    List<NoticeResponseDto> getNoticesByBranchName(SiDoType siDo, String branchName);
}