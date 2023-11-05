package com.yu.yurentcar.domain.car.controller;

import com.yu.yurentcar.domain.car.dto.KeyDto;
import com.yu.yurentcar.domain.car.dto.KeyManagementDto;
import com.yu.yurentcar.domain.car.service.KeyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/branches/cars/keys")
public class KeyController {
    public final KeyService keyService;


    public KeyController(KeyService keyService) {
        this.keyService = keyService;
    }

    @PostMapping
    public ResponseEntity<Long> postKey(@RequestBody KeyDto keyDto, @RequestParam String adminUsername) {
        log.info("postKey = " + keyDto);
        return ResponseEntity.status(HttpStatus.OK).body(keyService.postKey(adminUsername, keyDto));
    }

    @PatchMapping
    public ResponseEntity<Boolean> patchKey(@RequestBody KeyDto keyDto, @RequestParam String adminUsername, @RequestParam Long keyId) {
        log.info("patchKey = " + keyDto);
        return ResponseEntity.status(HttpStatus.OK).body(keyService.patchKey(adminUsername, keyDto, keyId));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteKey(@RequestParam String adminUsername, @RequestParam Long keyId) {
        log.info("deleteKey = " + keyId);
        return ResponseEntity.status(HttpStatus.OK).body(keyService.deleteKey(adminUsername, keyId));
    }

    @GetMapping("/management")
    public ResponseEntity<List<KeyManagementDto>> getKeyListByAdmin(@RequestParam String adminUsername) {
        log.info("getKeyListByAdmin = " + adminUsername);
        return ResponseEntity.status(HttpStatus.OK).body(keyService.getKeyListByAdmin(adminUsername));
    }

}