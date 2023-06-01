package com.yu.yurentcar.domain.user.controller;

import com.yu.yurentcar.domain.user.dto.SignupRequestDto;
import com.yu.yurentcar.domain.user.dto.UserProfileDto;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.domain.user.service.AuthService;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("api/v1/users")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/nicknames/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname) {
        log.info("nickname : " + nickname);
        return ResponseEntity.status(HttpStatus.OK).body(authService.checkNicknameDuplicate(nickname));
    }

    @PatchMapping(value = "/signup")
    public ResponseEntity<User> saveSignUpAdditionalInfo(@RequestBody SignupRequestDto signupRequestDto){
        log.info("data : "+signupRequestDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(authService.saveSignInAdditionalInfo(signupRequestDto));
    }
    @PatchMapping(value = "/nicknames/change")
    public ResponseEntity<User> changeNickname(@RequestBody UserProfileDto userProfileDto) {
        log.info("Username : " + userProfileDto.getUsername() + " nickname : " + userProfileDto.getNickname());
        return ResponseEntity.status(HttpStatus.OK).body(authService.changeNickname(userProfileDto.getUsername(), userProfileDto.getNickname()));
    }

    @PatchMapping(value = "/profiles/change")
    public ResponseEntity<User> changeUserProfile(@RequestBody UserProfileDto userProfileDto) {
        log.info(userProfileDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(authService.changeProfile(userProfileDto));
    }

    @GetMapping(value = "/point")
    public ResponseEntity<Integer> lookupPoint(@RequestParam String nickname){
        log.info("nickname : " + nickname);
        return ResponseEntity.status(HttpStatus.OK).body(authService.lookupPoint(nickname));
    }

}
