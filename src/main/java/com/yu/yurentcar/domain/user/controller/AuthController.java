package com.yu.yurentcar.domain.user.controller;

import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import com.yu.yurentcar.domain.user.dto.UserInfoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @PostMapping("user-info")
    public ResponseEntity<UserInfoDto> getSocialUserInfo(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        return ResponseEntity.ok(UserInfoDto.builder()
                .nickname(auth.getNickname())
                .username(auth.getUsername())
                .build());
    }
}
