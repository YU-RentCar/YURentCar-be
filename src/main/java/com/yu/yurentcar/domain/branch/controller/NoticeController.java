package com.yu.yurentcar.domain.branch.controller;

import com.yu.yurentcar.domain.branch.dto.NoticeContentResponseDto;
import com.yu.yurentcar.domain.branch.dto.NoticeResponseDto;
import com.yu.yurentcar.domain.branch.service.NoticeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<NoticeResponseDto>> getNoticesByBranchName(@RequestParam String province,@RequestParam String branchName) {
        log.info("branchName = " + branchName);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.getNoticesByBranchName(province,branchName));
    }

    @GetMapping("/details")
    public ResponseEntity<NoticeContentResponseDto> getNoticeContentByNoticeId(@RequestParam Long noticeId) {
        log.info("noticeId = " + noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.getNoticeContentByNoticeId(noticeId));
    }
}