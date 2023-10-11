package com.yu.yurentcar.domain.branch.service;

import com.yu.yurentcar.domain.branch.dto.NoticeDto;
import com.yu.yurentcar.domain.branch.dto.NoticeSummaryDto;
import com.yu.yurentcar.domain.branch.dto.NoticeResponseDto;
import com.yu.yurentcar.domain.branch.entity.Notice;
import com.yu.yurentcar.domain.branch.repository.NoticeRepository;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import com.yu.yurentcar.global.SiDoType;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public List<NoticeSummaryDto> getNoticesByBranchName(String province, String branchName, Integer count) {
        SiDoType siDo;
        try {
            siDo = EnumValueConvertUtils.ofDesc(SiDoType.class, province);
        } catch (RuntimeException e) {
            siDo = null;
        }
        return noticeRepository.getNoticesByBranchName(siDo, branchName, count);
    }

    @Transactional(readOnly = true)
    public NoticeResponseDto getNoticeContentByNoticeId(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("없는 공지사항입니다."));
        return NoticeResponseDto.builder()
                .photoUrl(notice.getPhotoUrl())
                .title(notice.getTitle())
                .description(notice.getDescription())
                .startDate(notice.getStartDate())
                .finishDate(notice.getFinishDate())
                .modifiedAt(notice.getModifiedAt())
                .build();
    }

    @Transactional
    public Long postNotice(NoticeDto noticeDto, String username) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(username);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        Notice notice = noticeRepository.save(
                Notice.builder()
                        .title(noticeDto.getTitle())
                        .branch(lookupAdmin.get().getBranch())
                        .admin(lookupAdmin.get())
                        .startDate(noticeDto.getStartDate())
                        .finishDate(noticeDto.getFinishDate())
                        .description(noticeDto.getDescription())
                        .photoUrl(noticeDto.getPhotoUrl())
                        .build()
        );
        return notice.getNoticeId();
    }

    @Transactional
    public Boolean patchNotice(NoticeDto noticeDto, String username, Long noticeId) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(username);
        Optional<Notice> lookupNotice = noticeRepository.findById(noticeId);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        if (lookupNotice.isEmpty()) throw new RuntimeException("없는 공지사항입니다.");
        //수정하려는 관리자와 작성한 관리자가 같은 지점 관리자인지 확인
        if (!lookupAdmin.get().getBranch().equals(lookupNotice.get().getBranch()))
            throw new RuntimeException("다른 지점 관리자입니다. 권한이 없습니다.");
        noticeRepository.save(lookupNotice.get().updateNotice(noticeDto, lookupAdmin.get()));
        return true;
    }

    @Transactional
    public Boolean deleteNotice(String username, Long noticeId) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(username);
        Optional<Notice> lookupNotice = noticeRepository.findById(noticeId);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        if (lookupNotice.isEmpty()) throw new RuntimeException("없는 공지사항입니다.");
        //수정하려는 관리자와 작성한 관리자가 같은 지점 관리자인지 확인
        if (!lookupAdmin.get().getBranch().equals(lookupNotice.get().getBranch()))
            throw new RuntimeException("다른 지점 관리자입니다. 권한이 없습니다.");
        noticeRepository.delete(lookupNotice.get());
        return true;
    }
}