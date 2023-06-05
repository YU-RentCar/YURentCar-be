package com.yu.yurentcar.domain.branch.service;

import com.yu.yurentcar.domain.branch.dto.NoticeContentResponseDto;
import com.yu.yurentcar.domain.branch.dto.NoticeResponseDto;
import com.yu.yurentcar.domain.branch.entity.Notice;
import com.yu.yurentcar.domain.branch.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<NoticeResponseDto> getNoticesByBranchName(String province, String branchName) {
        return noticeRepository.getNoticesByBranchName(province, branchName);
    }

    @Transactional(readOnly = true)
    public NoticeContentResponseDto getNoticeContentByNoticeId(Long noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        if (notice.isEmpty()) throw new RuntimeException();
        return NoticeContentResponseDto.builder()
                .title(notice.get().getTitle())
                .startDate(notice.get().getStartDate())
                .finishDate(notice.get().getFinishDate())
                .createdAt(notice.get().getCreatedAt())
                .modifiedAt(notice.get().getModifiedAt())
                .photoUrl(notice.get().getPhotoUrl())
                .description(notice.get().getDescription())
                .build();
    }
}