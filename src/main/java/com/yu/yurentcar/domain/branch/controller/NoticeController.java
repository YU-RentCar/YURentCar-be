package com.yu.yurentcar.domain.branch.controller;

import com.yu.yurentcar.domain.branch.dto.NoticeDto;
import com.yu.yurentcar.domain.branch.dto.NoticeListResponseDto;
import com.yu.yurentcar.domain.branch.dto.NoticeResponseDto;
import com.yu.yurentcar.domain.branch.service.NoticeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<NoticeListResponseDto>> getNoticesByBranchName(@RequestParam String province, @RequestParam String branchName, @RequestParam Integer count) {
        log.info("getNoticesByBranchName = " + province + "\n" + branchName + "\n" + count);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.getNoticesByBranchName(province, branchName,count));
    }

    @GetMapping("/details")
    public ResponseEntity<NoticeResponseDto> getNoticeContentByNoticeId(@RequestParam Long noticeId) {
        log.info("noticeId = " + noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.getNoticeContentByNoticeId(noticeId));
    }

    //쿠키생기면 @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth로 파라미터 수정
    @PostMapping
    public ResponseEntity<Long> postNotice(@RequestBody NoticeDto noticeDto, @RequestParam String username) {
        log.info("postNotice = " + noticeDto);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.postNotice(noticeDto, username));
    }

    //쿠키생기면 @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth로 파라미터 수정
    @PatchMapping
    public ResponseEntity<Boolean> patchNotice(@RequestBody NoticeDto noticeDto, @RequestParam String username, @RequestParam Long noticeId) {
        log.info("patchNotice = " + noticeDto);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.patchNotice(noticeDto, username, noticeId));
    }

    //쿠키생기면 @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth로 파라미터 수정
    @DeleteMapping
    public ResponseEntity<Boolean> deleteNotice(@RequestParam String username, @RequestParam Long noticeId) {
        log.info("deleteNotice = " + username + ", id = " + noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.deleteNotice(username, noticeId));
    }

}