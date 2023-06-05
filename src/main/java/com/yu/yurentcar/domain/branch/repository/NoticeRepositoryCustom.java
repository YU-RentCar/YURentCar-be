package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.dto.NoticeResponseDto;

import java.util.List;

public interface NoticeRepositoryCustom {
    List<NoticeResponseDto> getNoticesByBranchName(String province, String branchName);
}