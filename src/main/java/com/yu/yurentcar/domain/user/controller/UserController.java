package com.yu.yurentcar.domain.user.controller;

import com.yu.yurentcar.domain.user.dto.ChangeNicknameDto;
import com.yu.yurentcar.domain.user.dto.PreferFilterDto;
import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import com.yu.yurentcar.domain.user.dto.UserProfileDto;
import com.yu.yurentcar.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    // no auth
    @GetMapping(value = "/nicknames/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname) {
        log.info("new nickname : " + nickname);
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkNicknameDuplicate(nickname));
    }

    @GetMapping(value = "/prefer-filter")
    public ResponseEntity<PreferFilterDto> getPreferFilter(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPreferFilter(auth.getNickname()));
    }

    @PatchMapping(value = "/prefer-filter")
    public ResponseEntity<Boolean> changePreferFilter(@RequestBody PreferFilterDto preferFilterDto, @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("data : " + preferFilterDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.changePreferFilter(auth.getUsername(),preferFilterDto)
        );
    }

    @PatchMapping(value = "/nicknames")
    public ResponseEntity<Boolean> changeNickname(@RequestBody ChangeNicknameDto changeNicknameDto,
                                               @CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("Username : " + auth.getUsername() + " nickname : " + changeNicknameDto.getNickname());
        return ResponseEntity.status(HttpStatus.OK).body(userService.changeNickname(auth.getUsername(), changeNicknameDto.getNickname()));
    }

    // TODO : 추후 논의 후 추가
//    @PatchMapping(value = "/profiles")
//    public ResponseEntity<User> changeUserProfile(@RequestBody UserProfileDto userProfileDto) {
//        log.info("user profiles change : " + userProfileDto.toString());
//        return ResponseEntity.status(HttpStatus.OK).body(userService.changeProfile(userProfileDto));
//    }

    @GetMapping(value = "/point")
    public ResponseEntity<Integer> getPoint(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth) {
        log.info("username : " + auth.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPoint(auth.getUsername()));
    }

    @GetMapping(value = "/profiles")
    public ResponseEntity<UserProfileDto> getUserProfile(@CurrentSecurityContext(expression = "authentication.principal") UserAuthDto auth){
        log.info("username : " + auth.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfile(auth.getUsername()));
    }
}
