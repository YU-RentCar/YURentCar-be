package com.yu.yurentcar.domain.user.controller;

import com.yu.yurentcar.domain.user.dto.ChangeNicknameDto;
import com.yu.yurentcar.domain.user.dto.PreferFilterDto;
import com.yu.yurentcar.domain.user.dto.UserProfileDto;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.domain.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/nicknames/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname) {
        log.info("nickname : " + nickname);
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkNicknameDuplicate(nickname));
    }

    @PatchMapping(value = "/preferfilter")
    public ResponseEntity<User> changePreferFilter(@RequestParam String nickname,@RequestBody PreferFilterDto preferFilterDto) {
        log.info("data : " + preferFilterDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(userService.changePreferFilter(nickname,preferFilterDto));
    }
    @GetMapping(value = "/preferfilter")
    public ResponseEntity<PreferFilterDto> lookupPreferFilter(@RequestParam String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.lookupPreferFilter(nickname));
    }

    @PatchMapping(value = "/nicknames")
    public ResponseEntity<User> changeNickname(@RequestBody ChangeNicknameDto changeNicknameDto) {
        log.info("Username : " + changeNicknameDto.getUsername() + " nickname : " + changeNicknameDto.getNickname());
        return ResponseEntity.status(HttpStatus.OK).body(userService.changeNickname(changeNicknameDto));
    }

    @PatchMapping(value = "/profiles/change")
    public ResponseEntity<User> changeUserProfile(@RequestBody UserProfileDto userProfileDto) {
        log.info(userProfileDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(userService.changeProfile(userProfileDto));
    }

    @GetMapping(value = "/point")
    public ResponseEntity<Integer> lookupPoint(@RequestParam String nickname) {
        log.info("nickname : " + nickname);
        return ResponseEntity.status(HttpStatus.OK).body(userService.lookupPoint(nickname));
    }

    @GetMapping(value = "/profiles")
    public ResponseEntity<UserProfileDto> lookupUserProfile(@RequestParam String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.lookupUserProfile(username));
    }
}
