package com.yu.yurentcar.domain.user.controller;

import com.yu.yurentcar.domain.user.dto.AdminAuthDto;
import com.yu.yurentcar.domain.user.dto.AdminLoginDto;
import com.yu.yurentcar.domain.user.dto.AdminLoginResponseDto;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.service.AdminService;
import com.yu.yurentcar.global.utils.CookieProvider;
import com.yu.yurentcar.global.utils.TokenProvider;
import com.yu.yurentcar.security.dto.LoginCookies;
import com.yu.yurentcar.security.dto.TokenDto;
import com.yu.yurentcar.security.service.TokenRedisService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminService adminService;
    private final TokenProvider tokenProvider;
    private final TokenRedisService tokenRedisService;
    private final CookieProvider cookieProvider;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponseDto> loginAdmin(HttpServletResponse response, @RequestBody AdminLoginDto adminLoginDto) {
        log.info("loginAdmin : " + adminLoginDto);
        Admin admin = adminService.loginAdmin(adminLoginDto);

        AdminAuthDto adminAuth = new AdminAuthDto(
                admin.getUsername(),
                admin.getPassword(),
                admin.getBranch().getBranchId(),
                admin.getBranch().getBranchName(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        Authentication newAuth = new UsernamePasswordAuthenticationToken(adminAuth, "", adminAuth.getAuthorities());
        TokenDto token = tokenProvider.generateTokenDto(newAuth);
        tokenRedisService.saveToken(token);

        LoginCookies loginCookies = cookieProvider.makeLoginCookies(token);
        response.addHeader(HttpHeaders.SET_COOKIE, loginCookies.getAccessCookie().toString());
        response.addHeader(HttpHeaders.SET_COOKIE, loginCookies.getRefreshCookie().toString());

        return ResponseEntity.status(HttpStatus.OK).body(
                AdminLoginResponseDto.builder()
                        .branchName(admin.getBranch().getBranchName())
                        .province(admin.getBranch().getSiDo().getDesc())
                .build());
    }

}