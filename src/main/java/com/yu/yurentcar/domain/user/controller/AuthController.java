package com.yu.yurentcar.domain.user.controller;

import com.yu.yurentcar.domain.user.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class AuthController {
    private final AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/nicknames/exists")
     public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestBody String nickname){
        return ResponseEntity.status(HttpStatus.OK).body(authService.checkNicknameDuplicate(nickname));
    }
}
