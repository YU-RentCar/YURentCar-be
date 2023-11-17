package com.yu.yurentcar.domain.branch.service;

import com.yu.yurentcar.domain.branch.dto.NoticeDto;
import com.yu.yurentcar.domain.branch.dto.NoticePatchDto;
import com.yu.yurentcar.domain.branch.dto.NoticeResponseDto;
import com.yu.yurentcar.domain.branch.dto.NoticeSummaryDto;
import com.yu.yurentcar.domain.branch.entity.Notice;
import com.yu.yurentcar.domain.branch.repository.NoticeRepository;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import com.yu.yurentcar.global.SiDoType;
import com.yu.yurentcar.global.utils.FileUploadUtil;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

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
    public Long postNotice(NoticeDto noticeDto, String adminUsername, MultipartFile file) throws IOException {
        Admin admin = adminRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("없는 관리자입니다."));

        String projectPath;
        String fileName = "";

        if (file != null) {
            //저장할 경로를 지정
            projectPath = System.getProperty("user.dir") + "\\files";
            //식별자
            UUID uuid = UUID.randomUUID();
            //랜덤식별자_원래파일이름 = 저장될 파일이름 지정
            fileName = uuid + "_" + file.getOriginalFilename();
            //파일 저장
            FileUploadUtil.saveFile(projectPath, fileName, file);
        }

        Notice notice = noticeRepository.save(
                Notice.builder()
                        .title(noticeDto.getTitle())
                        .branch(admin.getBranch())
                        .admin(admin)
                        .startDate(noticeDto.getStartDate())
                        .finishDate(noticeDto.getFinishDate())
                        .description(noticeDto.getDescription())
                        .photoUrl(fileName)
                        .build()
        );
        return notice.getNoticeId();
    }

    @Transactional
    public Boolean patchNotice(NoticePatchDto noticePatchDto, String adminUsername, Long noticeId, MultipartFile file) throws IOException {
        Admin admin = adminRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("없는 관리자입니다."));
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("없는 공지사항입니다."));
        //수정하려는 관리자와 작성한 관리자가 같은 지점 관리자인지 확인
        if (!admin.getBranch().equals(notice.getBranch()))
            throw new RuntimeException("다른 지점 관리자입니다. 권한이 없습니다.");

        File lookupFile;
        String projectPath;
        String fileName = null;
        // 사진이 변경된 경우
        if (noticePatchDto.getIsModified()) {
            log.info("사진이 변경되었습니다.");
            // 기존의 사진이 있으면 지우기
            if (!notice.getPhotoUrl().equals("")) {
                log.info("기존의 이미지를 지웁니다.");
                log.info(System.getProperty("user.dir") + "\\files\\" + URLDecoder.decode(notice.getPhotoUrl(), "UTF-8"));
                try {
                    lookupFile = new File(System.getProperty("user.dir") + "\\files\\" + URLDecoder.decode(notice.getPhotoUrl(), "UTF-8"));
                    lookupFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (file != null) {
                //저장할 경로를 지정
                projectPath = System.getProperty("user.dir") + "\\files";
                //식별자
                UUID uuid = UUID.randomUUID();
                //랜덤식별자_원래파일이름 = 저장될 파일이름 지정
                fileName = uuid + "_" + file.getOriginalFilename();
                //파일 저장
                FileUploadUtil.saveFile(projectPath, fileName, file);
            }
        }

        noticeRepository.save(notice.updateNotice(NoticePatchDto.toNoticeDto(noticePatchDto), admin, fileName));

        return true;
    }

    @Transactional
    public Boolean deleteNotice(String adminUsername, Long noticeId) {
        Admin admin = adminRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("없는 관리자입니다."));
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("없는 공지사항입니다."));
        //수정하려는 관리자와 작성한 관리자가 같은 지점 관리자인지 확인
        if (!admin.getBranch().equals(notice.getBranch()))
            throw new RuntimeException("다른 지점 관리자입니다. 권한이 없습니다.");

        // 기존에 사진이 있는 경우 저장된 사진 삭제
        File lookupFile;
        if (!notice.getPhotoUrl().equals("")) {
            log.info("이미지를 지웁니다.");
            log.info(System.getProperty("user.dir") + "\\files\\" + URLDecoder.decode(notice.getPhotoUrl()));
            try {
                lookupFile = new File(System.getProperty("user.dir") + "\\files\\" + URLDecoder.decode(notice.getPhotoUrl(), "UTF-8"));
                lookupFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        noticeRepository.delete(notice);
        return true;
    }
}