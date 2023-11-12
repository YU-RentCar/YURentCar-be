package com.yu.yurentcar.domain.branch.controller;

import com.yu.yurentcar.domain.branch.dto.NoticeDto;
import com.yu.yurentcar.domain.branch.dto.NoticePatchDto;
import com.yu.yurentcar.domain.branch.dto.NoticeResponseDto;
import com.yu.yurentcar.domain.branch.dto.NoticeSummaryDto;
import com.yu.yurentcar.domain.branch.service.NoticeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/v1/branches/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<List<NoticeSummaryDto>> getNoticesByBranchName(@RequestParam String province, @RequestParam String branchName, @RequestParam Integer count) {
        log.info("getNoticesByBranchName = " + province + "\n" + branchName + "\n" + count);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.getNoticesByBranchName(province, branchName, count));
    }

    @GetMapping("/details")
    public ResponseEntity<NoticeResponseDto> getNoticeContentByNoticeId(@RequestParam Long noticeId) {
        log.info("noticeId = " + noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.getNoticeContentByNoticeId(noticeId));
    }

    //쿠키생기면 @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth로 파라미터 수정
    @PostMapping
    public ResponseEntity<Long> postNotice(@RequestPart(value = "noticeRequest") NoticeDto noticeDto,
                                           @RequestParam String adminUsername,
                                           @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        log.info("postNotice = " + noticeDto);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.postNotice(noticeDto, adminUsername, file));
    }

    //쿠키생기면 @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth로 파라미터 수정
    @PatchMapping
    public ResponseEntity<Boolean> patchNotice(@RequestPart(value = "noticeRequest") NoticePatchDto noticePatchDto,
                                               @RequestParam String adminUsername,
                                               @RequestParam Long noticeId,
                                               @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        log.info("patchNotice = " + noticePatchDto);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.patchNotice(noticePatchDto, adminUsername, noticeId, file));
    }

    //쿠키생기면 @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth로 파라미터 수정
    @DeleteMapping
    public ResponseEntity<Boolean> deleteNotice(@RequestParam String adminUsername, @RequestParam Long noticeId) {
        log.info("deleteNotice = " + adminUsername + ", id = " + noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.deleteNotice(adminUsername, noticeId));
    }

}