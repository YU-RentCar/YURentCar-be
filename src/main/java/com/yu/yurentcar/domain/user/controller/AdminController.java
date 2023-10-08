package com.yu.yurentcar.domain.user.controller;

import com.yu.yurentcar.domain.user.dto.AdminLoginDto;
import com.yu.yurentcar.domain.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Boolean> loginAdmin(@RequestBody AdminLoginDto adminLoginDto) {
        log.info("loginAdmin : " + adminLoginDto);
        return ResponseEntity.status(HttpStatus.OK).body(adminService.loginAdmin(adminLoginDto));
    }

}